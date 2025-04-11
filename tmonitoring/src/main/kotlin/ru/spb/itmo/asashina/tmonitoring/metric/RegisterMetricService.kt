package ru.spb.itmo.asashina.tmonitoring.metric

import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.BigInteger

@Component
class RegisterMetricService(
    private val meterRegistry: MeterRegistry
) {

    private val fraudTransactionCounter = meterRegistry.counter("fraud-transaction-counter")
    private val validTransactionCounter = meterRegistry.counter("valid-transaction-counter")
    private val errorTransactionCounter = meterRegistry.counter("error-transaction-counter")

    fun incrementFraudTransactions() = fraudTransactionCounter.increment()

    fun incrementValidTransactions() = validTransactionCounter.increment()

    fun incrementErrorTransactions() = errorTransactionCounter.increment()

    fun registerMaxAmount(label: String, amount: BigDecimal) {
        Gauge.builder("category_max_amount") { amount }
            .tag("t-category", label)
            .register(meterRegistry)
    }

    fun registerMinAmount(label: String, amount: BigDecimal) {
        Gauge.builder("category_min_amount") { amount }
            .tag("t-category", label)
            .register(meterRegistry)
    }

    fun registerSum(label: String, amount: BigDecimal) {
        Gauge.builder("category_sum") { amount }
            .tag("t-category", label)
            .register(meterRegistry)
    }

    fun registerCount(label: String, count: BigInteger) {
        Gauge.builder("category_count") { count }
            .tag("t-category", label)
            .register(meterRegistry)
    }

}