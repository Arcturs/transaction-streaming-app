package ru.spb.itmo.asashina.tproducer.listener

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import okhttp3.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.spb.itmo.asashina.tproducer.model.entity.Transaction
import ru.spb.itmo.asashina.tproducer.repository.TransactionRepository
import java.util.concurrent.TimeUnit

@Component
class TransactionStreamWebSocketListener(
    private val objectMapper: ObjectMapper,
    private val transactionRepository: TransactionRepository,
    @Value("\${generator.url}") private val generatorUrl: String
) {

    private val client = OkHttpClient.Builder()
        .readTimeout(100, TimeUnit.MILLISECONDS)
        .build()

    @PostConstruct
    fun init() {
        val request = Request.Builder()
            .url(generatorUrl)
            .build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                log.info("Connected to {}", generatorUrl)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                saveMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                log.info("Connection closing: $code/$reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                log.warn("Connection error: {}", t.message, t)
            }
        }

        client.newWebSocket(request, listener)
    }

    @Transactional
    fun saveMessage(message: String) {
        transactionRepository.save(
            objectMapper.readValue(message, Transaction::class.java)
        )
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(TransactionStreamWebSocketListener::class.java)
    }

}