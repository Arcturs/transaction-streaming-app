package ru.spb.itmo.asashina.tproducer.listener

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.chunked
import kotlinx.coroutines.flow.retryWhen
import okhttp3.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import ru.spb.itmo.asashina.tproducer.model.entity.Transaction
import ru.spb.itmo.asashina.tproducer.repository.TransactionRepository
import java.util.concurrent.TimeUnit

@Component
class TransactionStreamWebSocketListener(
    private val objectMapper: ObjectMapper,
    private val transactionRepository: TransactionRepository,
    private val transactionalOperator: TransactionalOperator,
    @Value("\${generator.url}") private val generatorUrl: String
) {

    private val client = OkHttpClient().newBuilder()
        .readTimeout(500, TimeUnit.MILLISECONDS)
        .build()

    private var webSocket: WebSocket? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val messages: Flow<String> = callbackFlow {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                log.info("Connection opened")
                this@TransactionStreamWebSocketListener.webSocket = webSocket
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                trySend(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                close()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                close(t)
            }
        }

        val request = Request.Builder()
            .url(generatorUrl)
            .build()
        webSocket = client.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "Spring context closing")
            client.dispatcher.executorService.use {
                it.close()
            }
        }
    }

    @PostConstruct
    @OptIn(ExperimentalCoroutinesApi::class)
    fun init() {
        scope.launch {
            while (isActive) {
                runCatching {
                    messages
                        .retryWhen { cause, attempt ->
                            println("WebSocket error: ${cause.message}, retry #$attempt")
                            delay(5000)
                            true
                        }
                        .chunked(100)
                        .collect { saveMessages(it) }
                }.onFailure {
                    scope.cancel("Spring context shutdown")
                    webSocket?.close(1000, "Application shutdown")
                }
            }
        }
    }

    @PreDestroy
    fun cleanup() {
        scope.cancel("Spring context shutdown")
        webSocket?.close(1000, "Application shutdown")
    }

    private suspend fun saveMessages(messages: List<String>) {
        transactionalOperator.executeAndAwait {
            transactionRepository.saveAll(
                messages.map { objectMapper.readValue(it, Transaction::class.java) }
            )
        }
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(TransactionStreamWebSocketListener::class.java)
    }

}