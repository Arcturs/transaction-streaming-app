package ru.spb.itmo.asashina.tgenerator.generator

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tgenerator.message.TransactionMessage
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.*


@Component
class TransactionMessageGenerator {

    fun generateMessages() = List(10_000) {
        EASY_RANDOM.nextObject(TransactionMessage::class.java).apply {
            originId = CLIENT_IDS.random()
            destinationId = CLIENT_IDS.random()
            timestamp = LocalDateTime.now()
            amount = BigDecimal(Random().nextDouble(AMOUNT_RANDOM_SEED)).setScale(2, RoundingMode.HALF_UP)
            originOldBalance = BigDecimal(Random().nextDouble(AMOUNT_EXTENDED_RANDOM_SEED))
                .plus(amount!!)
                .setScale(2, RoundingMode.HALF_UP)
            originNewBalance = originOldBalance!!.minus(amount!!).setScale(2, RoundingMode.HALF_UP)
            destinationOldBalance = BigDecimal(Random().nextDouble(AMOUNT_EXTENDED_RANDOM_SEED))
                .minus(amount!!)
                .setScale(2, RoundingMode.HALF_UP)
            destinationNewBalance = destinationOldBalance!!.plus(amount!!).setScale(2)
        }
    }

    private companion object {
        const val AMOUNT_RANDOM_SEED = 100_000_000_001.00
        const val AMOUNT_EXTENDED_RANDOM_SEED = 100_000_000_000_001.00

        val CLIENT_IDS = List(10_000) { UUID.randomUUID() }

        @JvmStatic
        val EASY_RANDOM = EasyRandom(EasyRandomParameters().seed(Date().time))
    }

}