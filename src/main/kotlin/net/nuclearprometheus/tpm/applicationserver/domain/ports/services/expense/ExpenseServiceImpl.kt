package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import java.math.BigDecimal
import java.time.ZonedDateTime

class ExpenseServiceImpl(
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val expenseCategoryRepository: ExpenseCategoryRepository,
    private val currencyRepository: CurrencyRepository,
    private val logger: Logger
) : ExpenseService {

    override fun create(
        description: String,
        category: ExpenseCategoryId,
        amount: BigDecimal,
        currencyCode: CurrencyCode,
        date: ZonedDateTime,
        teamMemberId: TeamMemberId,
        projectId: ProjectId
    ): Expense {
        projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId does not exist")
        teamMemberRepository.get(teamMemberId) ?: throw NotFoundException("Team member with id $teamMemberId does not exist")

        val expenseCategory = expenseCategoryRepository.get(category) ?: throw NotFoundException("Expense category with id $category does not exist")
        val currency = currencyRepository.get(currencyCode) ?: throw NotFoundException("Currency with code $currencyCode does not exist")

        val expense = Expense(
            projectId = projectId,
            teamMemberId = teamMemberId,
            description = description,
            amount = amount,
            date = date,
            category = expenseCategory,
            currency = currency
        )

        return expenseRepository.create(expense)
    }

    override fun delete(id: ExpenseId) {
        expenseRepository.delete(id)
    }
}
