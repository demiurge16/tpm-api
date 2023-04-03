package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import java.time.ZonedDateTime

class ExpenseServiceImpl(
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val logger: Logger
) : ExpenseService {

    override fun create(
        projectId: ProjectId,
        teamMemberId: TeamMemberId,
        description: String,
        amount: Double,
        date: ZonedDateTime
    ): Expense {
        projectRepository.get(projectId) ?: throw IllegalArgumentException("Project with id $projectId does not exist")
        teamMemberRepository.get(teamMemberId) ?: throw IllegalArgumentException("Team member with id $teamMemberId does not exist")

        val expense = Expense(
            projectId = projectId,
            teamMemberId = teamMemberId,
            description = description,
            amount = amount,
            date = date
        )

        return expenseRepository.create(expense)
    }

    override fun delete(id: ExpenseId) {
        expenseRepository.delete(id)
    }
}
