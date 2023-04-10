package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectApplicationService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{id}")
class ProjectStatusController(
    private val service: ProjectApplicationService
) {

    @PatchMapping("/finish-draft")
    fun finishDraft(@PathVariable(name = "id") id: UUID) = service.finishDraft(id)

    @PatchMapping("/back-to-draft")
    fun backToDraft(@PathVariable(name = "id") id: UUID) = service.backToDraft(id)

    @PatchMapping("/start-progress")
    fun startProgress(@PathVariable(name = "id") id: UUID) = service.startProgress(id)

    @PatchMapping("/finish-progress")
    fun finishProgress(@PathVariable(name = "id") id: UUID) = service.finishProgress(id)

    @PatchMapping("/back-to-progress")
    fun backToProgress(@PathVariable(name = "id") id: UUID) = service.backToProgress(id)

    @PatchMapping("/deliver")
    fun deliver(@PathVariable(name = "id") id: UUID) = service.deliver(id)

    @PatchMapping("/invoice")
    fun invoice(@PathVariable(name = "id") id: UUID) = service.invoice(id)

    @PatchMapping("/pay")
    fun pay(@PathVariable(name = "id") id: UUID) = service.pay(id)

    @PatchMapping("/put-on-hold")
    fun putOnHold(@PathVariable(name = "id") id: UUID) = service.putOnHold(id)

    @PatchMapping("/resume")
    fun resume(@PathVariable(name = "id") id: UUID) = service.resume(id)

    @PatchMapping("/cancel")
    fun cancel(@PathVariable(name = "id") id: UUID) = service.cancel(id)

    @PatchMapping("/reopen")
    fun reopen(@PathVariable(name = "id") id: UUID) = service.reopen(id)
}