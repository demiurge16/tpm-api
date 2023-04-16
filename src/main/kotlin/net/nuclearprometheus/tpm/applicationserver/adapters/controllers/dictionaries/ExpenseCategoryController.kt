package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.ExpenseCategoryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ExpenseCategoryRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/expense-category")
class ExpenseCategoryController(private val service: ExpenseCategoryApplicationService) {

    private val logger = loggerFor(ExpenseCategoryController::class.java)

    @GetMapping("")
    fun getAll() = with(logger) {
        info("GET /api/v1/expense-category")

        service.getExpenseCategories()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/expense-category/$id")

        service.getExpenseCategory(id)
    }

    @PostMapping("")
    fun create(@RequestBody request: ExpenseCategoryRequest.Create) = with(logger) {
        info("POST /api/v1/expense-category")

        service.createExpenseCategory(request)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(name = "id") id: UUID, @RequestBody request: ExpenseCategoryRequest.Update) = with(logger) {
        info("PUT /api/v1/expense-category/$id")

        service.updateExpenseCategory(id, request)
    }

    @PatchMapping("/{id}/activate")
    fun activate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/expense-category/$id/activate")

        service.activateExpenseCategory(id)
    }

    @PatchMapping("/{id}/deactivate")
    fun deactivate(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/expense-category/$id/deactivate")

        service.deactivateExpenseCategory(id)
    }
}

