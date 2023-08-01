package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectExpenseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectExpenseRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.Pageable
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.emptyPage
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectExpenseApplicationService(
    private val expenseService: ExpenseService,
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(ProjectExpenseApplicationService::class.java)

    fun getExpensesForProject(projectId: UUID): Pageable<ExpenseResponse.Expense> {
        logger.info("getExpensesForProject($projectId)")

        val project = projectRepository.get(ProjectId(projectId))

        if (project == null) {
            logger.warn("Project with id $projectId not found")
            return emptyPage()
        }

        return singlePage(
            expenseRepository.getAllByProjectId(ProjectId(projectId)).map {
                val teamMember = project.teamMembers.find { teamMember -> teamMember.id == it.teamMemberId }
                    ?: throw IllegalStateException("Team member with id ${it.teamMemberId} not found in project ${project.id}")

                it.toView(teamMember, project)
            }
        )
    }

    fun createExpense(projectId: UUID, request: ProjectExpenseRequest.Create): ExpenseResponse.Expense {
        logger.info("createExpense($projectId, $request)")

        return expenseService.create(
            description = request.description,
            category = ExpenseCategoryId(request.category),
            amount = request.amount,
            currencyCode = CurrencyCode(request.currency),
            date = request.date,
            teamMemberId = TeamMemberId(request.teamMemberId),
            projectId = ProjectId(projectId)
        ).let {
            val project = projectRepository.get(ProjectId(projectId))
            val teamMember = project?.teamMembers?.find { teamMember -> teamMember.id == it.teamMemberId }
                ?: throw IllegalStateException("Team member with id ${it.teamMemberId} not found in project ${project?.id}")

            it.toView(teamMember, project)
        }
    }
}
