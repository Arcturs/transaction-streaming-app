package ru.spb.itmo.asashina.tconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.spb.itmo.asashina.tconnector.config.FlinkKafkaConsumerProperties;
import ru.spb.itmo.asashina.tconnector.config.FlinkKafkaProducerProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FlinkKafkaConsumerProperties.class, FlinkKafkaProducerProperties.class })
public class TconnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TconnectorApplication.class, args);
    }

}
