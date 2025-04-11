package ru.spb.itmo.asashina.tmonitoring.model.message

import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class CategoryStatsKafkaMessage(
    var category: String? = null,
    var count: BigInteger? = null,
    var maxAmount: BigDecimal? = null,
    var minAmount: BigDecimal? = null,
    var sum: BigDecimal? = null,
    var timestamp: Long? = null
) {

    val actualTimestamp by lazy {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp!!), ZoneId.of("UTC-3"))
    }

}
