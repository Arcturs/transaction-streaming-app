package ru.spb.itmo.asashina.tmonitoring.metric

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class RegisterMetricService(
    meterRegistry: MeterRegistry
) {

    private val fraudTransactionCounter = meterRegistry.counter("fraud-transaction-counter")
    private val validTransactionCounter = meterRegistry.counter("valid-transaction-counter")
    private val errorTransactionCounter = meterRegistry.counter("error-transaction-counter")

    fun incrementFraudTransactions() = fraudTransactionCounter.increment()

    fun incrementValidTransactions() = validTransactionCounter.increment()

    fun incrementErrorTransactions() = errorTransactionCounter.increment()

}