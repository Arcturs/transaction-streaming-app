package ru.spb.itmo.asashina.tconnector.model.message;

import java.util.UUID;

public class FraudDetectionResultMessage {

    private UUID id;
    private Long timestamp;
    private String result;

    public FraudDetectionResultMessage(UUID id, Long timestamp, String result) {
        this.id = id;
        this.timestamp = timestamp;
        this.result = result;
    }

    public FraudDetectionResultMessage() {
    }

    public UUID getId() {
        return id;
    }

    public FraudDetectionResultMessage setId(UUID id) {
        this.id = id;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public FraudDetectionResultMessage setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getResult() {
        return result;
    }

    public FraudDetectionResultMessage setResult(String result) {
        this.result = result;
        return this;
    }
}
