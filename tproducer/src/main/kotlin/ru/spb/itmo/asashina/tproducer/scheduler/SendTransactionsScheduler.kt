package ru.spb.itmo.asashina.tproducer.scheduler

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import org.springframework.stereotype.Component

@Component
class SendTransactionsScheduler {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @PostConstruct
    fun init() {
        scope.launch {
            while (isActive) {
                runCatching {
                    //TODO
                }.onFailure {
                    scope.cancel("Spring context shutdown")
                }
            }
        }
    }

    @PreDestroy
    fun cleanup() {
        scope.cancel("Spring context shutdown")
    }

}