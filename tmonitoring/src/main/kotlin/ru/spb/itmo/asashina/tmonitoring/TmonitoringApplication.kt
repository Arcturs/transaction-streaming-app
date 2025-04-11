package ru.spb.itmo.asashina.tmonitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class TmonitoringApplication

fun main(args: Array<String>) {
	runApplication<TmonitoringApplication>(*args)
}
