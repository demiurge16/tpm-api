package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectExpenseApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectExpenseRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/expense")
class ProjectExpenseController(private val service: ProjectExpenseApplicationService) {

    private val logger = loggerFor(ProjectExpenseController::class.java)

    @GetMapping("")
    fun getExpenses(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/expense")

        ResponseEntity.ok().body(service.getExpensesForProject(projectId))
    }

    @PostMapping("")
    fun createExpense(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: ProjectExpenseRequest.Create) =
        with(logger) {
            info("POST /api/v1/project/$projectId/expense")

            ResponseEntity.ok().body(service.createExpense(projectId, request))
        }
}

