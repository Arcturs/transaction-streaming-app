package ru.spb.itmo.asashina.tconnector.model.message;

import java.math.BigDecimal;

public class CategoryStatMessage {

    private String category;
    private Long count;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private BigDecimal averageAmount;
    private Long timestamp;

    public CategoryStatMessage(
            String category, Long count,
            BigDecimal maxAmount, BigDecimal minAmount,
            BigDecimal averageAmount, Long timestamp) {

        this.category = category;
        this.count = count;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.averageAmount = averageAmount;
        this.timestamp = timestamp;
    }

    public CategoryStatMessage() {
    }

    public String getCategory() {
        return category;
    }

    public CategoryStatMessage setCategory(String category) {
        this.category = category;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public CategoryStatMessage setCount(Long count) {
        this.count = count;
        return this;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public CategoryStatMessage setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
        return this;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public CategoryStatMessage setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public BigDecimal getAverageAmount() {
        return averageAmount;
    }

    public CategoryStatMessage setAverageAmount(BigDecimal averageAmount) {
        this.averageAmount = averageAmount;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public CategoryStatMessage setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
