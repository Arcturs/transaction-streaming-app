package ru.spb.itmo.asashina.tproducer.scheduler

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tproducer.model.message.KafkaTransactionMessage
import ru.spb.itmo.asashina.tproducer.producer.KafkaTransactionProducer
import ru.spb.itmo.asashina.tproducer.repository.TransactionRepository

@Component
class SendTransactionsScheduler(
    private val kafkaProducer: KafkaTransactionProducer,
    private val transactionRepository: TransactionRepository
) {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @PostConstruct
    fun init() {
        log.info("Here!")
        scope.launch {
            log.info("Here!")
            while (currentCoroutineContext().isActive) {
                runCatching {
                    sendTransactions()
                    delay(500)
                }.onFailure {
                    scope.cancel("Spring context shutdown")
                }
            }
        }
    }

    suspend fun sendTransactions() {
        val transactions = withContext(Dispatchers.IO) {
            transactionRepository.getTransactionsInBatch(DEFAULT_BATCH_SIZE)
        }
        if (transactions.isEmpty()) {
            log.info("no transactions")
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
                log.info("sending")
                kafkaProducer.sendMessage(it)
            }
            .map { it.id!! }

        withContext(Dispatchers.IO) {
            transactionRepository.deleteByIdIn(sentTransactionIds)
        }

        log.info("wow")
    }

    @PreDestroy
    fun cleanup() {
        scope.cancel("Spring context shutdown")
    }

    private companion object {
        const val DEFAULT_BATCH_SIZE = 100

        val log: Logger = LoggerFactory.getLogger(SendTransactionsScheduler::class.java)
    }

}