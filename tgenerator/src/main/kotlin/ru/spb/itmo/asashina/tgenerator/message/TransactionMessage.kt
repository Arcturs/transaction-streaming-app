package ru.spb.itmo.asashina.tgenerator.message

import ru.spb.itmo.asashina.tgenerator.dictionary.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TransactionMessage(
    var id: UUID? = null,
    var timestamp: LocalDateTime = LocalDateTime.now(),
    var type: TransactionType? = null,
    var amount: BigDecimal? = null,
    var currency: CurrencyType? = null,
    var originId: UUID? = null,
    var destinationId: UUID? = null,
    var originOldBalance: BigDecimal? = null,
    var originNewBalance: BigDecimal? = null,
    var destinationOldBalance: BigDecimal? = null,
    var destinationNewBalance: BigDecimal? = null,
    var cardType: CardType? = null,
    var clientCategory: ClientCategory? = null,
    var sex: SexType? = null,
    var paymentCategory: PaymentCategory? = null,
    var clientType: ClientType? = null
)
