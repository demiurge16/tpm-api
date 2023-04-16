package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ExpenseCategoryRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ExpenseCategoryResponseMapper.toActivityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ExpenseCategoryResponseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ExpenseCategoryService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseCategoryApplicationService(
    private val service: ExpenseCategoryService,
    private val repository: ExpenseCategoryRepository
) {

    private val logger = loggerFor(ExpenseCategoryApplicationService::class.java)

    fun getExpenseCategories() = with(logger) {
        info("getExpenseCategories()")

        singlePage(repository.getAll()).map { it.toView() }
    }

    fun getExpenseCategory(id: UUID) = with(logger) {
        info("getExpenseCategory($id)")

        repository.get(ExpenseCategoryId(id))?.toView() ?: throw NotFoundException("Expense category with id $id not found")
    }

    fun createExpenseCategory(request: ExpenseCategoryRequest.Create) = with(logger) {
        info("createExpenseCategory($request)")

        service.create(
            request.name,
            request.description
        ).toView()
    }

    fun updateExpenseCategory(id: UUID, request: ExpenseCategoryRequest.Update) = with(logger) {
        info("updateExpenseCategory($id, $request)")

        service.update(
            ExpenseCategoryId(id),
            request.name,
            request.description
        ).toView()
    }

    fun activateExpenseCategory(id: UUID) = with(logger) {
        info("activateExpenseCategory($id)")

        service.activate(ExpenseCategoryId(id)).toActivityStatus()
    }

    fun deactivateExpenseCategory(id: UUID) = with(logger) {
        info("deactivateExpenseCategory($id)")

        service.deactivate(ExpenseCategoryId(id)).toActivityStatus()
    }
}