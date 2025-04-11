package ru.spb.itmo.asashina.tmonitoring.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.spb.itmo.asashina.tmonitoring.dictionary.FraudDetectionResultType
import ru.spb.itmo.asashina.tmonitoring.model.entity.FraudDetectionEntity.Companion.TABLE_NAME
import java.time.LocalDateTime
import java.util.UUID

@Table(TABLE_NAME)
class FraudDetectionEntity {

    @get:Id
    @get:Column
    var id: UUID? = null

    @get:Column
    var timestamp: LocalDateTime? = null

    @get:Column
    var result: FraudDetectionResultType? = null

    @get:Column
    var showed: Boolean? = false

    override fun equals(other: Any?) = id == (other as? FraudDetectionEntity)?.id

    override fun hashCode() = id.hashCode()

    companion object {
        const val TABLE_NAME = "fraud_detection"
    }

}