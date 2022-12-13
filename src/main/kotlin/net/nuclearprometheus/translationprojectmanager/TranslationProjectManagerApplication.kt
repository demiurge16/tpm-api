package net.nuclearprometheus.translationprojectmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class TranslationProjectManagerApplication

fun main(args: Array<String>) {
    runApplication<TranslationProjectManagerApplication>(*args)
}
