package ru.spb.itmo.asashina.tconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.spb.itmo.asashina.tconnector.config.FlinkKafkaCategoryStatsProducerProperties;
import ru.spb.itmo.asashina.tconnector.config.FlinkKafkaConsumerProperties;
import ru.spb.itmo.asashina.tconnector.config.FlinkKafkaFraudDetectionProducerProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FlinkKafkaConsumerProperties.class, FlinkKafkaFraudDetectionProducerProperties.class,
        FlinkKafkaCategoryStatsProducerProperties.class })
public class TconnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TconnectorApplication.class, args);
    }

}
