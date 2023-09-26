package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses.Expense as ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers.ExpenseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.CreateExpense
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.emptyPage
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ProjectExpenseApplicationService(
    private val expenseService: ExpenseService,
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ProjectExpenseApplicationService::class.java)

    fun getExpensesForProject(projectId: UUID, request: FilteredRequest<Expense>): Page<ExpenseResponse> {
        logger.info("getExpensesForProject($projectId)")

        val project = projectRepository.get(ProjectId(projectId))

        if (project == null) {
            logger.warn("Project with id $projectId not found")
            return emptyPage()
        }

        return expenseRepository.getAllByProjectIdAndQuery(ProjectId(projectId), request.toQuery()).map {
            val user = project.teamMembers.map { it.user }
                .distinctBy { it.id }
                .find { user -> user.id == it.spender.id }
                ?: throw IllegalStateException("Team member with id ${it.spender.id} not found in project with id ${it.projectId}")

            it.toView(user, project)
        }
    }

    fun createExpense(projectId: UUID, request: CreateExpense): ExpenseResponse {
        logger.info("createExpense($projectId, $request)")

        return expenseService.create(
            description = request.description,
            category = ExpenseCategoryId(request.category),
            amount = request.amount,
            currencyCode = CurrencyCode(request.currency),
            date = request.date,
            spenderId = UserId(request.spenderId),
            projectId = ProjectId(projectId)
        ).let {
            val project = projectRepository.get(ProjectId(projectId))
                ?: throw IllegalStateException("Project with id $projectId not found")
            val user = project.teamMembers.map { it.user }
                .distinctBy { it.id }
                .find { user -> user.id == it.spender.id }
                ?: throw IllegalStateException("Team member with id ${it.spender.id} not found in project with id ${it.projectId}")

            it.toView(user, project)
        }
    }
}
