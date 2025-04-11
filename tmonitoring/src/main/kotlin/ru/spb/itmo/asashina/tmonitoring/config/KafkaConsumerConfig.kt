package ru.spb.itmo.asashina.tmonitoring.config

import org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter

@Configuration
class KafkaConsumerConfig(
    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private val bootstrapServers: String,
    @Value("\${spring.kafka.consumer.category-stats.group-id}")
    private val categoryStatsGroupId: String,
    @Value("\${spring.kafka.consumer.fraud-detection.group-id}")
    private val fraudDetectionGroupId: String,
    @Value("\${spring.kafka.consumer.category-stats.max-poll-records}")
    private val categoryStatsMaxPollRecords: String,
    @Value("\${spring.kafka.consumer.fraud-detection.max-poll-records}")
    private val fraudDetectionMaxPollRecords: String
) {

    @Bean
    fun kafkaFraudDetectionListenerContainerFactory() =
        ConcurrentKafkaListenerContainerFactory<String, ByteArray>()
            .apply {
                consumerFactory = consumerFraudDetectionFactory()
                isBatchListener = true
                setBatchMessageConverter(BatchMessagingMessageConverter(StringJsonMessageConverter()))
                containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
                setConcurrency(4)
            }

    @Bean
    fun consumerFraudDetectionFactory(): ConsumerFactory<String, ByteArray> =
        DefaultKafkaConsumerFactory(
            mapOf<String, Any>(
                BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                GROUP_ID_CONFIG to fraudDetectionGroupId,
                KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                VALUE_DESERIALIZER_CLASS_CONFIG to ByteArrayDeserializer::class.java,
                MAX_POLL_RECORDS_CONFIG to fraudDetectionMaxPollRecords,
                ENABLE_AUTO_COMMIT_CONFIG to false,
                AUTO_OFFSET_RESET_CONFIG to "earliest"
            )
        )

    @Bean
    fun kafkaCategoryStatsListenerContainerFactory() =
        ConcurrentKafkaListenerContainerFactory<String, ByteArray>()
            .apply {
                consumerFactory = consumerCategoryStatsFactory()
                isBatchListener = true
                setBatchMessageConverter(BatchMessagingMessageConverter(StringJsonMessageConverter()))
                containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
                setConcurrency(4)
            }

    @Bean
    fun consumerCategoryStatsFactory(): ConsumerFactory<String, ByteArray> =
        DefaultKafkaConsumerFactory(
            mapOf<String, Any>(
                BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                GROUP_ID_CONFIG to categoryStatsGroupId,
                KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                VALUE_DESERIALIZER_CLASS_CONFIG to ByteArrayDeserializer::class.java,
                MAX_POLL_RECORDS_CONFIG to categoryStatsMaxPollRecords,
                ENABLE_AUTO_COMMIT_CONFIG to false,
                AUTO_OFFSET_RESET_CONFIG to "earliest"
            )
        )

}