package ru.spb.itmo.asashina.tconnector.model.message;

import java.math.BigDecimal;
import java.util.UUID;

public class KafkaTransactionMessage {

    private UUID id;
    private int[] timestamp;
    private String type;
    private BigDecimal amount;
    private UUID originId;
    private UUID destinationId;
    private BigDecimal originOldBalance;
    private BigDecimal originNewBalance;
    private BigDecimal destinationOldBalance;
    private BigDecimal destinationNewBalance;
    private String cardType;
    private String paymentCategory;

    public KafkaTransactionMessage() {
    }

    public KafkaTransactionMessage(
            UUID id, int[] timestamp,
            String type, BigDecimal amount,
            UUID originId, UUID destinationId,
            BigDecimal originOldBalance,
            BigDecimal originNewBalance,
            BigDecimal destinationOldBalance,
            BigDecimal destinationNewBalance,
            String cardType, String paymentCategory) {

        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.originId = originId;
        this.destinationId = destinationId;
        this.originOldBalance = originOldBalance;
        this.originNewBalance = originNewBalance;
        this.destinationOldBalance = destinationOldBalance;
        this.destinationNewBalance = destinationNewBalance;
        this.cardType = cardType;
        this.paymentCategory = paymentCategory;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int[] getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int[] timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getOriginId() {
        return originId;
    }

    public void setOriginId(UUID originId) {
        this.originId = originId;
    }

    public UUID getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(UUID destinationId) {
        this.destinationId = destinationId;
    }

    public BigDecimal getOriginOldBalance() {
        return originOldBalance;
    }

    public void setOriginOldBalance(BigDecimal originOldBalance) {
        this.originOldBalance = originOldBalance;
    }

    public BigDecimal getOriginNewBalance() {
        return originNewBalance;
    }

    public void setOriginNewBalance(BigDecimal originNewBalance) {
        this.originNewBalance = originNewBalance;
    }

    public BigDecimal getDestinationOldBalance() {
        return destinationOldBalance;
    }

    public void setDestinationOldBalance(BigDecimal destinationOldBalance) {
        this.destinationOldBalance = destinationOldBalance;
    }

    public BigDecimal getDestinationNewBalance() {
        return destinationNewBalance;
    }

    public void setDestinationNewBalance(BigDecimal destinationNewBalance) {
        this.destinationNewBalance = destinationNewBalance;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(String paymentCategory) {
        this.paymentCategory = paymentCategory;
    }
}
