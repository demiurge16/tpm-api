package net.nuclearprometheus.tpm.applicationserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableFeignClients
@EnableCaching
class ApplicationServerApplication

fun main(args: Array<String>) {
    runApplication<ApplicationServerApplication>(*args)
}
