package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/expense")
class ProjectExpenseController {

    @GetMapping("")
    fun getExpenses(@PathVariable(name = "projectId") projectId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun createExpense(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: Any) {
        TODO()
    }
}