package ru.spb.itmo.asashina.tproducer.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import ru.spb.itmo.asashina.tproducer.model.message.KafkaTransactionMessage


@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.topic}") private val topic: String,
    @Value("\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String
) {

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, KafkaTransactionMessage?> = KafkaTemplate(producerFactory())

    @Bean
    fun producerFactory(): ProducerFactory<String, KafkaTransactionMessage?> =
        DefaultKafkaProducerFactory(
            mapOf<String, Any>(
                BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                BATCH_SIZE_CONFIG to "10000"
            ),
            StringSerializer(),
            JsonSerializer<KafkaTransactionMessage?>().apply {
                isAddTypeInfo = false
            }
        )

    @Bean
    fun admin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers))

    @Bean
    fun defaultTopic() =
        TopicBuilder.name(topic)
            .partitions(3)
            .replicas(2)
            .compact()
            .build()

}