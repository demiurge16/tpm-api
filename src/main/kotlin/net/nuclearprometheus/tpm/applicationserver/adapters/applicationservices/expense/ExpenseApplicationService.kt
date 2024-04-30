package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers.ExpenseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses.Expense as ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ExpenseApplicationService(
    private val repository: ExpenseRepository,
    private val service: ExpenseService,
    private val projectRepository: ProjectRepository,
    private val specificationBuilder: SpecificationBuilder<Expense>
) {

    private val logger = loggerFor(ExpenseApplicationService::class.java)

    fun getExpenses(query: FilteredRequest<Expense>): Page<ExpenseResponse> {
        logger.info("getExpenses($query)")
        return repository.get(query.toQuery(specificationBuilder))
            .map {
                val project = projectRepository.get(it.projectId)
                    ?: throw IllegalStateException("Project with id ${it.projectId} not found")
                val user = project.teamMembers.map { it.user }
                    .distinctBy { it.id }
                    .find { user -> user.id == it.spender.id }
                    ?: throw IllegalStateException("Team member with id ${it.spender.id} not found in project with id ${it.projectId}")

                it.toView(user, project)
            }
    }

    fun getExpense(expenseId: UUID): ExpenseResponse {
        logger.info("getExpense($expenseId)")
        return repository.get(ExpenseId(expenseId))
            ?.let {
                val project = projectRepository.get(it.projectId)
                    ?: throw IllegalStateException("Project with id ${it.projectId} not found")
                val user = project.teamMembers.map { it.user }
                    .distinctBy { it.id }
                    .find { user -> user.id == it.spender.id }
                    ?: throw IllegalStateException("Team member with id ${it.spender.id} not found in project with id ${it.projectId}")

                it.toView(user, project)
            } ?: throw NotFoundException("Expense with id $expenseId not found")
    }

    fun deleteExpense(expenseId: UUID) {
        logger.info("deleteExpense($expenseId)")
        service.delete(ExpenseId(expenseId))
    }
}