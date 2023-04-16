package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers.ExpenseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseApplicationService(private val repository: ExpenseRepository, private val service: ExpenseService) {

    private val logger = loggerFor(ExpenseApplicationService::class.java)

    fun getExpenses() = with(logger) {
        info("getExpenses()")

        singlePage(repository.getAll()).map { it.toView() }
    }

    fun getExpense(expenseId: UUID) = with(logger) {
        info("getExpense($expenseId)")

        repository.get(ExpenseId(expenseId))?.toView() ?: throw NotFoundException("Expense with id $expenseId not found")
    }

    fun deleteExpense(expenseId: UUID) = with(logger) {
        info("deleteExpense($expenseId)")

        service.delete(ExpenseId(expenseId))
    }
}