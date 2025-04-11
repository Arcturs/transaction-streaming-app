package ru.spb.itmo.asashina.tmonitoring.scheduler

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.spb.itmo.asashina.tmonitoring.metric.RegisterMetricService
import ru.spb.itmo.asashina.tmonitoring.repository.CategoryStatRepository

@Async
@Component
class CategoryStatsScheduler(
    private val repository: CategoryStatRepository,
    private val registerMetricService: RegisterMetricService
) {

    @Scheduled(fixedDelay = 5_000, initialDelay = 1_200)
    fun countCategoryResult() {
        val results = repository.findActualWithLimit(100)
        if (results.isEmpty()) {
            return
        }

        for (result in results) {
            val category = result.category!!
            registerMetricService.registerMaxAmount(category, result.maxAmount!!)
            registerMetricService.registerMinAmount(category, result.minAmount!!)
            registerMetricService.registerSum(category, result.sum!!)
            registerMetricService.registerCount(category, result.count!!)
        }

        repository.saveAll(
            results.map {
                it.apply { showed = true }
            }
        )
    }

}