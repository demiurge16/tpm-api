package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskRefdataApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/task/refdata")
class TaskRefdataController(private val service: TaskRefdataApplicationService) {

    private val logger = loggerFor(TaskRefdataController::class.java)

    @GetMapping("/status")
    fun getStatuses() = with(logger) {
        info("GET /api/v1/task/refdata/status")

        ResponseEntity.ok().body(service.getStatuses())
    }
}

