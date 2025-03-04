package ru.spb.itmo.asashina.tproducer.producer

import kotlinx.coroutines.future.await
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tproducer.model.message.KafkaTransactionMessage


@Component
class KafkaTransactionProducer(
    @Value("\${spring.kafka.topic}") private val topic: String,
    private val eventKafkaTemplate: KafkaTemplate<String, KafkaTransactionMessage>
) {

    suspend fun sendMessage(message: KafkaTransactionMessage): Boolean {
        return eventKafkaTemplate.send(topic, message)
            .handle { _, exception: Throwable? ->
                if (exception != null) {
                    log.warn("Произошла ошибка при попытке отправить сообщение в топик", exception)
                    return@handle false
                }
                true
            }
            .await()
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(KafkaTransactionProducer::class.java)
    }

}