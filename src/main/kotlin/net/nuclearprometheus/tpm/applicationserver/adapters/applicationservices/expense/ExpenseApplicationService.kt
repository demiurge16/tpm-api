package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers.ExpenseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.*

@Service
class ExpenseApplicationService(
    private val repository: ExpenseRepository,
    private val service: ExpenseService,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ExpenseApplicationService::class.java)

    fun getExpenses() = with(logger) {
        info("getExpenses()")

        singlePage(repository.getAll()).map {
            val project = projectRepository.get(it.projectId)
                ?: throw IllegalStateException("Project with id ${it.projectId} not found")
            val user = project.teamMembers.map { it.user }
                .distinctBy { it.id }
                .find { user -> user.id == it.spender.id }
                ?: throw IllegalStateException("Team member with id ${it.spender.id} not found in project with id ${it.projectId}")

            it.toView(user, project)
        }
    }

    fun getExpense(expenseId: UUID) = with(logger) {
        info("getExpense($expenseId)")

        repository.get(ExpenseId(expenseId))?.let {
            val project = projectRepository.get(it.projectId)
                ?: throw IllegalStateException("Project with id ${it.projectId} not found")
            val user = project.teamMembers.map { it.user }
                .distinctBy { it.id }
                .find { user -> user.id == it.spender.id }
                ?: throw IllegalStateException("Team member with id ${it.spender.id} not found in project with id ${it.projectId}")

            it.toView(user, project)
        } ?: throw NotFoundException("Expense with id $expenseId not found")
    }

    fun deleteExpense(expenseId: UUID) = with(logger) {
        info("deleteExpense($expenseId)")

        service.delete(ExpenseId(expenseId))
    }
}