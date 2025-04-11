package ru.spb.itmo.asashina.tmonitoring.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.spb.itmo.asashina.tmonitoring.model.entity.CategoryStatEntity.Companion.TABLE_NAME
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*

@Table(TABLE_NAME)
class CategoryStatEntity {

    @get:Id
    @get:Column
    var id: UUID? = null

    @get:Column
    var timestamp: LocalDateTime? = null

    @get:Column
    var category: String? = null

    @get:Column
    var count: BigInteger? = null

    @get:Column
    var maxAmount: BigDecimal? = null

    @get:Column
    var minAmount: BigDecimal? = null

    @get:Column
    var sum: BigDecimal? = null

    @get:Column
    var showed: Boolean? = false

    override fun equals(other: Any?) = id == (other as? CategoryStatEntity)?.id

    override fun hashCode() = id.hashCode()

    companion object {
        const val TABLE_NAME = "category_stats"
    }

}