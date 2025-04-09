package ru.spb.itmo.asashina.tconnector.config;

import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spb.itmo.asashina.tconnector.model.message.KafkaTransactionMessage;

@Configuration
public class FlinkKafkaConsumerConfig {

    private final FlinkKafkaConsumerProperties properties;

    public FlinkKafkaConsumerConfig(FlinkKafkaConsumerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public KafkaSource<KafkaTransactionMessage> kafkaFraudDetectionSource() {
        return KafkaSource.<KafkaTransactionMessage>builder()
                .setBootstrapServers(properties.getBootstrapServers())
                .setTopics(properties.getTopic())
                .setGroupId(properties.getGroupId() + "-0")
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(KafkaTransactionMessage.class))
                .build();
    }

    @Bean
    public KafkaSource<KafkaTransactionMessage> kafkaCategoryStatsSource() {
        return KafkaSource.<KafkaTransactionMessage>builder()
                .setBootstrapServers(properties.getBootstrapServers())
                .setTopics(properties.getTopic())
                .setGroupId(properties.getGroupId() + "-1")
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(KafkaTransactionMessage.class))
                .build();
    }

}
