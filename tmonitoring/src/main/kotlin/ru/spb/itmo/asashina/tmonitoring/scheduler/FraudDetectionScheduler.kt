package ru.spb.itmo.asashina.tmonitoring.scheduler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tmonitoring.dictionary.FraudDetectionResultType.*
import ru.spb.itmo.asashina.tmonitoring.metric.RegisterMetricService
import ru.spb.itmo.asashina.tmonitoring.repository.FraudDetectionRepository

@Async
@Component
class FraudDetectionScheduler(
    private val repository: FraudDetectionRepository,
    private val registerMetricService: RegisterMetricService
) {

    @Scheduled(fixedDelay = 2_500, initialDelay = 1_000)
    fun countTransactionsResult() {
        val results = repository.findActualWithLimit(100)
        log.info("empty!")
        if (results.isEmpty()) {
            return
        }

        for (result in results) {
            when(result.result!!) {
                FRAUD -> registerMetricService.incrementFraudTransactions()
                NOT_FRAUD -> registerMetricService.incrementValidTransactions()
                ERROR -> registerMetricService.incrementErrorTransactions()
            }
        }

        repository.saveAll(
            results.map {
                it.apply { showed = true }
            }
        )
        log.info("Done!")
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

}