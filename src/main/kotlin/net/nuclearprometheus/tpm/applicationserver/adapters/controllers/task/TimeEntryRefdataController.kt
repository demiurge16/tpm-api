package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TimeEntryRefdataApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeUnit
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/time-entry/refdata")
class TimeEntryRefdataController(private val service: TimeEntryRefdataApplicationService) {

    private val logger = loggerFor(TimeEntryRefdataController::class.java)

    @GetMapping("/status")
    fun getTimeEntryStatuses(): ResponseEntity<List<TimeEntryStatus>> {
        logger.info("GET /api/v1/time-entry/refdata/status")
        return ResponseEntity.ok().body(service.getTimeEntryStatuses())
    }

    @GetMapping("/time-unit")
    fun getTimeEntryTimeUnits(): ResponseEntity<List<TimeUnit>> {
        logger.info("GET /api/v1/time-entry/refdata/time-unit")
        return ResponseEntity.ok().body(service.getTimeEntryTimeUnits())
    }
}