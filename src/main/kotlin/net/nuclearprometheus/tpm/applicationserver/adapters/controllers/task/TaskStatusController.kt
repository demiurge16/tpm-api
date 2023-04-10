package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/task/{taskId}")
class TaskStatusController {

    @PatchMapping("/{taskId}/start")
    fun start(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/complete")
    fun complete(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/request-revisions")
    fun requestRevisions(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/complete-revisions")
    fun completeRevisions(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/deliver")
    fun deliver(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/cancel")
    fun cancel(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/reopen")
    fun reopen(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }
}