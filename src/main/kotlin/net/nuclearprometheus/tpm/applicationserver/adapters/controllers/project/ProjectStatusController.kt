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
@RequestMapping("/api/v1/project/{projectId}")
class ProjectStatusController(
    private val service: ProjectStatusApplicationService
) {

    private val logger = loggerFor(ProjectStatusController::class.java)

    @PatchMapping("/finish-draft")
    fun finishDraft(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/finish-draft")

        ResponseEntity.ok().body(service.finishDraft(id))
    }

    @PatchMapping("/back-to-draft")
    fun backToDraft(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/back-to-draft")

        ResponseEntity.ok().body(service.backToDraft(id))
    }

    @PatchMapping("/start-progress")
    fun startProgress(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/start-progress")

        ResponseEntity.ok().body(service.startProgress(id))
    }

    @PatchMapping("/start-review")
    fun finishProgress(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/start-review")

        ResponseEntity.ok().body(service.startReview(id))
    }

    @PatchMapping("/approve")
    fun backToProgress(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/back-to-progress")

        ResponseEntity.ok().body(service.approve(id))
    }

    @PatchMapping("/reject")
    fun reject(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/reject")

        ResponseEntity.ok().body(service.reject(id))
    }

    @PatchMapping("/deliver")
    fun deliver(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/deliver")

        ResponseEntity.ok().body(service.deliver(id))
    }

    @PatchMapping("/invoice")
    fun invoice(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/invoice")

        ResponseEntity.ok().body(service.invoice(id))
    }

    @PatchMapping("/pay")
    fun pay(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/pay")

        ResponseEntity.ok().body(service.pay(id))
    }

    @PatchMapping("/put-on-hold")
    fun putOnHold(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/put-on-hold")

        ResponseEntity.ok().body(service.putOnHold(id))
    }

    @PatchMapping("/resume")
    fun resume(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/resume")

        ResponseEntity.ok().body(service.resume(id))
    }

    @PatchMapping("/cancel")
    fun cancel(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/cancel")

        ResponseEntity.ok().body(service.cancel(id))
    }

    @PatchMapping("/reopen")
    fun reopen(@PathVariable(name = "projectId") id: UUID) = with(logger) {
        info("PATCH /api/v1/project/$id/reopen")

        ResponseEntity.ok().body(service.reopen(id))
    }
}
