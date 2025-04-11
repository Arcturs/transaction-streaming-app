package ru.spb.itmo.asashina.tconnector.model.message;

import java.math.BigDecimal;

public class CategoryStatMessage {

    private String category;
    private Long count;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private BigDecimal sum;
    private Long timestamp;

    public CategoryStatMessage(
            String category, Long count,
            BigDecimal maxAmount, BigDecimal minAmount,
            BigDecimal sum, Long timestamp) {

        this.category = category;
        this.count = count;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.sum = sum;
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

    public BigDecimal getSum() {
        return sum;
    }

    public CategoryStatMessage setSum(BigDecimal sum) {
        this.sum = sum;
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
