package ru.spb.itmo.asashina.tconnector.config;

import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spb.itmo.asashina.tconnector.model.message.CategoryStatMessage;
import ru.spb.itmo.asashina.tconnector.model.message.FraudDetectionResultMessage;

@Configuration
public class FlinkKafkaProducerConfig {

    private final FlinkKafkaFraudDetectionProducerProperties fraudDetectionProducerProperties;
    private final FlinkKafkaCategoryStatsProducerProperties categoryStatsProducerProperties;

    public FlinkKafkaProducerConfig(
            FlinkKafkaFraudDetectionProducerProperties fraudDetectionProducerProperties,
            FlinkKafkaCategoryStatsProducerProperties categoryStatsProducerProperties) {

        this.fraudDetectionProducerProperties = fraudDetectionProducerProperties;
        this.categoryStatsProducerProperties = categoryStatsProducerProperties;
    }

    @Bean
    public KafkaSink<FraudDetectionResultMessage> kafkaFraudDetectionSink() {
        return KafkaSink.<FraudDetectionResultMessage>builder()
                .setBootstrapServers(fraudDetectionProducerProperties.getBootstrapServers())
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(fraudDetectionProducerProperties.getTopic())
                        .setValueSerializationSchema(new JsonSerializationSchema<FraudDetectionResultMessage>())
                        .build()
                )
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }

    @Bean
    public KafkaSink<CategoryStatMessage> kafkaCategoryStatsSink() {
        return KafkaSink.<CategoryStatMessage>builder()
                .setBootstrapServers(categoryStatsProducerProperties.getBootstrapServers())
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(categoryStatsProducerProperties.getTopic())
                        .setValueSerializationSchema(new JsonSerializationSchema<CategoryStatMessage>())
                        .build()
                )
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }

}
