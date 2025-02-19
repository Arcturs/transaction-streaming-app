package ru.spb.itmo.asashina.tgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TgeneratorApplication

fun main(args: Array<String>) {
    runApplication<TgeneratorApplication>(*args)
}
