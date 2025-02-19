package ru.spb.itmo.asashina.tgenerator.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.spb.itmo.asashina.tgenerator.handler.TransactionStreamWebSocketHandler

@Configuration
@EnableWebSocket
class WebSocketConfig(private val streamHandler: TransactionStreamWebSocketHandler) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(streamHandler, "/transactions")
            .setAllowedOrigins("*")
    }

}