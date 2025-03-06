package ru.spb.itmo.asashina.tproducer.scheduler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.spb.itmo.asashina.tproducer.model.message.KafkaTransactionMessage
import ru.spb.itmo.asashina.tproducer.producer.KafkaTransactionProducer
import ru.spb.itmo.asashina.tproducer.repository.TransactionRepository

@Async
@Component
class SendTransactionsScheduler(
    private val kafkaProducer: KafkaTransactionProducer,
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    @Scheduled(fixedDelay = 500)
    fun sendTransactions() {
        val transactions = transactionRepository.getTransactionsInBatch(DEFAULT_BATCH_SIZE)
        if (transactions.isEmpty()) {
            return
        }

        val sentTransactionIds = transactions.map {
            KafkaTransactionMessage().apply {
                id = it.id
                timestamp = it.timestamp
                type = it.type
                amount = it.amount
                originId = it.originId
                destinationId = it.destinationId
                originOldBalance = it.originOldBalance
                originNewBalance = it.originNewBalance
                destinationOldBalance = it.destinationOldBalance
                destinationNewBalance = it.destinationNewBalance
                cardType = it.cardType
            }
        }
            .filter {
                kafkaProducer.sendMessage(it)
            }
            .map { it.id!! }

        transactionRepository.deleteAllByIdIn(sentTransactionIds)
    }

    private companion object {
        const val DEFAULT_BATCH_SIZE = 100

        val log: Logger = LoggerFactory.getLogger(SendTransactionsScheduler::class.java)
    }

}