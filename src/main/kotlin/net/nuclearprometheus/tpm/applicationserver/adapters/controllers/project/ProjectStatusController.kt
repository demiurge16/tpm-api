package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectStatusApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{id}")
class ProjectStatusController(
    private val service: ProjectStatusApplicationService
) {

    private val logger = loggerFor(ProjectStatusController::class.java)

    @PatchMapping("/finish-draft")
    fun finishDraft(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/finish-draft")

        ResponseEntity.ok().body(service.finishDraft(id))
    }

    @PatchMapping("/back-to-draft")
    fun backToDraft(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/back-to-draft")

        ResponseEntity.ok().body(service.backToDraft(id))
    }

    @PatchMapping("/start-progress")
    fun startProgress(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/start-progress")

        ResponseEntity.ok().body(service.startProgress(id))
    }

    @PatchMapping("/finish-progress")
    fun finishProgress(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/finish-progress")

        ResponseEntity.ok().body(service.finishProgress(id))
    }

    @PatchMapping("/back-to-progress")
    fun backToProgress(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/back-to-progress")

        ResponseEntity.ok().body(service.backToProgress(id))
    }

    @PatchMapping("/deliver")
    fun deliver(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/deliver")

        ResponseEntity.ok().body(service.deliver(id))
    }

    @PatchMapping("/invoice")
    fun invoice(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/invoice")

        ResponseEntity.ok().body(service.invoice(id))
    }

    @PatchMapping("/pay")
    fun pay(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/pay")

        ResponseEntity.ok().body(service.pay(id))
    }

    @PatchMapping("/put-on-hold")
    fun putOnHold(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/put-on-hold")

        ResponseEntity.ok().body(service.putOnHold(id))
    }

    @PatchMapping("/resume")
    fun resume(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/resume")

        ResponseEntity.ok().body(service.resume(id))
    }

    @PatchMapping("/cancel")
    fun cancel(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/cancel")

        ResponseEntity.ok().body(service.cancel(id))
    }

    @PatchMapping("/reopen")
    fun reopen(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/reopen")

        ResponseEntity.ok().body(service.reopen(id))
    }
}