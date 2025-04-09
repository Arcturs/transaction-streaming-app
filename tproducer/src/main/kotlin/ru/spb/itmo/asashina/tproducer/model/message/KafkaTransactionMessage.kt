package ru.spb.itmo.asashina.tproducer.model.message

import ru.spb.itmo.asashina.tproducer.dictionary.CardType
import ru.spb.itmo.asashina.tproducer.dictionary.PaymentCategory
import ru.spb.itmo.asashina.tproducer.dictionary.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class KafkaTransactionMessage(
    var id: UUID? = null,
    var timestamp: LocalDateTime? = null,
    var type: TransactionType? = null,
    var amount: BigDecimal? = null,
    var originId: UUID? = null,
    var destinationId: UUID? = null,
    var originOldBalance: BigDecimal? = null,
    var originNewBalance: BigDecimal? = null,
    var destinationOldBalance: BigDecimal? = null,
    var destinationNewBalance: BigDecimal? = null,
    var cardType: CardType? = null,
    var paymentCategory: PaymentCategory? = null
)
