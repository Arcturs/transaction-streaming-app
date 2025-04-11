package ru.spb.itmo.asashina.tmonitoring.model.message

import ru.spb.itmo.asashina.tmonitoring.dictionary.FraudDetectionResultType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

data class FraudDetectionKafkaMessage(
    var id: UUID? = null,
    var timestamp: Long? = null,
    var result: String? = null
) {

    val actualTimestamp by lazy {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp!!), ZoneId.of("UTC-3"))
    }

    val actualResult by lazy { FraudDetectionResultType.fromCode(result!!) }

}