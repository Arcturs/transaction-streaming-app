package ru.spb.itmo.asashina.tmonitoring.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tmonitoring.model.entity.FraudDetectionEntity
import ru.spb.itmo.asashina.tmonitoring.model.message.FraudDetectionKafkaMessage
import ru.spb.itmo.asashina.tmonitoring.repository.FraudDetectionRepository

@Component
class FraudDetectionKafkaConsumer(
    private val repository: FraudDetectionRepository,
    private val objectMapper: ObjectMapper = ObjectMapper()
) {

    @KafkaListener(
        topics = ["\${spring.kafka.consumer.fraud-detection.topic}"],
        groupId = "\${spring.kafka.consumer.fraud-detection.group-id}",
        containerFactory = "kafkaFraudDetectionListenerContainerFactory"
    )
    fun process(messages: List<ConsumerRecord<String, ByteArray>>, acknowledgment: Acknowledgment) {
        runCatching {
            messages.map {
                val mappedMessage = objectMapper.readValue(it.value(), FraudDetectionKafkaMessage::class.java)
                return@map FraudDetectionEntity().apply {
                    id = mappedMessage.id
                    timestamp = mappedMessage.actualTimestamp
                    result = mappedMessage.actualResult
                }
            }
                .forEach { repository.saveWithoutConflict(it.id!!, it.timestamp!!, it.result!!) }
            acknowledgment.acknowledge()
        }.onFailure {
            log.error("Error in processing messages", it)
        }
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

}