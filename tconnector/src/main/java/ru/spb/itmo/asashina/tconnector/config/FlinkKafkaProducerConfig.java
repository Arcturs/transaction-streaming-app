package ru.spb.itmo.asashina.tconnector.config;

import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spb.itmo.asashina.tconnector.model.message.FraudDetectionResultMessage;

@Configuration
public class FlinkKafkaProducerConfig {

    private final FlinkKafkaProducerProperties properties;

    public FlinkKafkaProducerConfig(FlinkKafkaProducerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public KafkaSink<FraudDetectionResultMessage> kafkaFraudDetectionSink() {
        return KafkaSink.<FraudDetectionResultMessage>builder()
                .setBootstrapServers(properties.getBootstrapServers())
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(properties.getTopic())
                        .setValueSerializationSchema(new JsonSerializationSchema<FraudDetectionResultMessage>())
                        .build()
                )
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }

}
