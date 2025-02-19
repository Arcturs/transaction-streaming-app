package ru.spb.itmo.asashina.tgenerator.handler

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import ru.spb.itmo.asashina.tgenerator.generator.TransactionMessageGenerator
import java.util.concurrent.ConcurrentHashMap

@Component
class TransactionStreamWebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val messageGenerator: TransactionMessageGenerator
) : BinaryWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        log.info("Установка сессии {}", session.id)
        sessions[session.id] = session
        scope.launch { sendStream(session) }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
        session.close()
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        session.close()
    }

    private suspend fun sendStream(session: WebSocketSession) {
        while(session.isOpen) {
            messageGenerator.generateMessages().forEach {
                if (!session.isOpen) {
                    return
                }

                session.sendMessage(
                    TextMessage(objectMapper.writeValueAsBytes(it))
                )
            }
            delay(500)
        }
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(TransactionStreamWebSocketHandler::class.java)
    }

}