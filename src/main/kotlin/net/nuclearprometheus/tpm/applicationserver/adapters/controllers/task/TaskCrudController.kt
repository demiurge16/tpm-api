package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/task")
class TaskCrudController {

    @GetMapping("")
    fun getTasks() {
        TODO()
    }

    @GetMapping("/{taskId}")
    fun getTask(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @DeleteMapping("/{taskId}")
    fun deleteTask(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/move-start")
    fun moveStart(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }

    @PatchMapping("/{taskId}/move-deadline")
    fun moveDeadline(@PathVariable(name = "taskId") taskId: UUID) {
        TODO()
    }
}

