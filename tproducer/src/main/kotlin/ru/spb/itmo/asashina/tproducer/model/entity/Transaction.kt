package ru.spb.itmo.asashina.tproducer.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.spb.itmo.asashina.tproducer.dictionary.*
import ru.spb.itmo.asashina.tproducer.model.entity.Transaction.Companion.TABLE_NAME
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table(TABLE_NAME)
class Transaction {

    @get:Id
    @get:Column
    var systemId: UUID? = null

    @get:Column
    var id: UUID? = null

    @get:Column
    var timestamp: LocalDateTime = LocalDateTime.now()

    @get:Column
    var type: TransactionType? = null

    @get:Column
    var amount: BigDecimal? = null

    @get:Column
    var currency: CurrencyType? = null

    @get:Column
    var originId: UUID? = null

    @get:Column
    var destinationId: UUID? = null

    @get:Column
    var originOldBalance: BigDecimal? = null

    @get:Column
    var originNewBalance: BigDecimal? = null

    @get:Column
    var destinationOldBalance: BigDecimal? = null

    @get:Column
    var destinationNewBalance: BigDecimal? = null

    @get:Column
    var cardType: CardType? = null

    @get:Column
    var clientCategory: ClientCategory? = null

    @get:Column
    var sex: SexType? = null

    @get:Column
    var paymentCategory: PaymentCategory? = null

    @get:Column
    var clientType: ClientType? = null

    override fun equals(other: Any?) = id == (other as? Transaction)?.id

    override fun hashCode() = id.hashCode()

    companion object {
        const val TABLE_NAME = "transactions"
    }

}