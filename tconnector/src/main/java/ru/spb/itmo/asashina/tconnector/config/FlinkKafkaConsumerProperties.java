package ru.spb.itmo.asashina.tconnector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class FlinkKafkaConsumerProperties {

    private String bootstrapServers;
    private String groupId;
    private String topic;

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getTopic() {
        return topic;
    }
}
