package ru.spb.itmo.asashina.tmonitoring.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tmonitoring.model.entity.CategoryStatEntity
import ru.spb.itmo.asashina.tmonitoring.model.message.CategoryStatsKafkaMessage
import ru.spb.itmo.asashina.tmonitoring.repository.CategoryStatRepository

@Component
class CategoryStatsKafkaConsumer(
    private val repository: CategoryStatRepository,
    private val objectMapper: ObjectMapper = ObjectMapper()
) {

    @KafkaListener(
        topics = ["\${spring.kafka.consumer.category-stats.topic}"],
        groupId = "\${spring.kafka.consumer.category-stats.group-id}",
        containerFactory = "kafkaCategoryStatsListenerContainerFactory"
    )
    fun process(messages: List<ConsumerRecord<String, ByteArray>>, acknowledgment: Acknowledgment) {
        runCatching {
            log.info("I have messages!")
            messages.map {
                val mappedMessage = objectMapper.readValue(it.value(), CategoryStatsKafkaMessage::class.java)
                log.info("I mapped!")
                return@map CategoryStatEntity().apply {
                    timestamp = mappedMessage.actualTimestamp
                    count = mappedMessage.count
                    maxAmount = mappedMessage.maxAmount
                    minAmount = mappedMessage.minAmount
                    sum = mappedMessage.sum
                    category = mappedMessage.category
                }
            }
                .forEach {
                    repository.saveWithoutConflict(
                        it.timestamp!!, it.category!!, it.count!!,
                        it.maxAmount!!, it.minAmount!!, it.sum!!
                    )
                }
            acknowledgment.acknowledge()
            log.info("DONE!")
        }.onFailure {
            log.error("Error in processing messages", it)
        }
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

}