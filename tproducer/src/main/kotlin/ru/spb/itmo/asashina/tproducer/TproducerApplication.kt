package ru.spb.itmo.asashina.tproducer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class TproducerApplication

fun main(args: Array<String>) {
	runApplication<TproducerApplication>(*args)
}
