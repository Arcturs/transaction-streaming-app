package ru.spb.itmo.asashina.tproducer.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.spb.itmo.asashina.tgenerator.dictionary.*
import ru.spb.itmo.asashina.tproducer.model.entity.Transaction.Companion.TABLE_NAME
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table(TABLE_NAME)
class Transaction {

    @get:Id
    @get:Column
    val id: UUID? = null

    @get:Column
    val timestamp: LocalDateTime = LocalDateTime.now()

    @get:Column
    val type: TransactionType? = null

    @get:Column
    val amount: BigDecimal? = null

    @get:Column
    val currency: CurrencyType? = null

    @get:Column
    val originId: UUID? = null

    @get:Column
    val destinationId: UUID? = null

    @get:Column
    val originOldBalance: BigDecimal? = null

    @get:Column
    val originNewBalance: BigDecimal? = null

    @get:Column
    val destinationOldBalance: BigDecimal? = null

    @get:Column
    val destinationNewBalance: BigDecimal? = null

    @get:Column
    val cardType: CardType? = null

    @get:Column
    val clientCategory: ClientCategory? = null

    @get:Column
    val sex: SexType? = null

    @get:Column
    val paymentCategory: PaymentCategory? = null

    @get:Column
    val clientType: ClientType? = null

    override fun equals(other: Any?) = id == (other as? Transaction)?.id

    override fun hashCode() = id.hashCode()

    companion object {
        const val TABLE_NAME = "transactions"
    }

}