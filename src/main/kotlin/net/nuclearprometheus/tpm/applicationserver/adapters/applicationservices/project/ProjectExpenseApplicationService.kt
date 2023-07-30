package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectExpenseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectExpenseRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectExpenseApplicationService(
    private val expenseService: ExpenseService,
    private val expenseRepository: ExpenseRepository
) {

    private val logger = loggerFor(ProjectExpenseApplicationService::class.java)

    fun getExpensesForProject(projectId: UUID) = with(logger) {
        info("getExpensesForProject($projectId)")

        singlePage(expenseRepository.getAllByProjectId(ProjectId(projectId)).map { it.toView() })
    }

    fun createExpense(projectId: UUID, request: ProjectExpenseRequest.Create) = with(logger) {
        info("createExpense($projectId, $request)")

        expenseService.create(
            description = request.description,
            category = ExpenseCategoryId(request.category),
            amount = request.amount,
            currencyCode = CurrencyCode(request.currency),
            date = request.date,
            teamMemberId = TeamMemberId(request.teamMemberId),
            projectId = ProjectId(projectId)
        ).toView()
    }

}