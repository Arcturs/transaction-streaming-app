package ru.spb.itmo.asashina.tconnector.model.request;

import java.math.BigDecimal;
import java.util.List;

public class FraudDetectionApiRequest {

    private List<String> type;
    private List<BigDecimal> amount;
    private List<BigDecimal> originOldBalance;
    private List<BigDecimal> originNewBalance;
    private List<BigDecimal> destinationOldBalance;
    private List<BigDecimal> destinationNewBalance;

    public List<String> getType() {
        return type;
    }

    public FraudDetectionApiRequest setType(List<String> type) {
        this.type = type;
        return this;
    }

    public List<BigDecimal> getAmount() {
        return amount;
    }

    public FraudDetectionApiRequest setAmount(List<BigDecimal> amount) {
        this.amount = amount;
        return this;
    }

    public List<BigDecimal> getOriginOldBalance() {
        return originOldBalance;
    }

    public FraudDetectionApiRequest setOriginOldBalance(List<BigDecimal> originOldBalance) {
        this.originOldBalance = originOldBalance;
        return this;
    }

    public List<BigDecimal> getOriginNewBalance() {
        return originNewBalance;
    }

    public FraudDetectionApiRequest setOriginNewBalance(List<BigDecimal> originNewBalance) {
        this.originNewBalance = originNewBalance;
        return this;
    }

    public List<BigDecimal> getDestinationOldBalance() {
        return destinationOldBalance;
    }

    public FraudDetectionApiRequest setDestinationOldBalance(List<BigDecimal> destinationOldBalance) {
        this.destinationOldBalance = destinationOldBalance;
        return this;
    }

    public List<BigDecimal> getDestinationNewBalance() {
        return destinationNewBalance;
    }

    public FraudDetectionApiRequest setDestinationNewBalance(List<BigDecimal> destinationNewBalance) {
        this.destinationNewBalance = destinationNewBalance;
        return this;
    }
}
