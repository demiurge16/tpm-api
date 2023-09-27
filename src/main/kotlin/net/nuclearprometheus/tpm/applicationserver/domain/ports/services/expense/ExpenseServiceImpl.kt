package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.expense.ExpenseAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import java.math.BigDecimal
import java.time.ZonedDateTime

class ExpenseServiceImpl(
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val expenseCategoryRepository: ExpenseCategoryRepository,
    private val currencyRepository: CurrencyRepository,
    private val userContextProvider: UserContextProvider,
    private val logger: Logger
) : ExpenseService {

    override fun create(
        description: String,
        category: ExpenseCategoryId,
        amount: BigDecimal,
        currencyCode: CurrencyCode,
        date: ZonedDateTime,
        spenderId: UserId,
        projectId: ProjectId
    ): Expense {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId does not exist")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} tried to create expense for project ${project.id} without being a project member")
            throw ProjectAccessException("User ${currentUser.id} tried to create expense for project ${project.id} without being a project member")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) && currentUser.id != spenderId) {
            logger.error("User ${currentUser.id} tried to create expense for project ${project.id} for another user without being a project manager")
            throw ProjectAccessException("User ${currentUser.id} tried to create expense for project ${project.id} for another user without being a project manager")
        }

        val spender = userRepository.get(spenderId) ?: throw NotFoundException("User with id $spenderId does not exist")

        if (!project.hasTeamMember(spender.id)) {
            logger.error("User ${currentUser.id} tried to create expense for project ${project.id} for user ${spender.id}, but user ${spender.id} is not a project member")
            throw ProjectAccessException("User ${currentUser.id} tried to create expense for project ${project.id} for user ${spender.id}, but user ${spender.id} is not a project member")
        }

        val expense = Expense(
            projectId = projectId,
            spender = spender,
            description = description,
            amount = amount,
            date = date,
            category = expenseCategoryRepository.get(category)
                ?: throw NotFoundException("Expense category with id $category does not exist"),
            currency = currencyRepository.get(currencyCode)
                ?: throw NotFoundException("Currency with code $currencyCode does not exist")
        )

        return expenseRepository.create(expense)
    }

    override fun delete(id: ExpenseId) {
        val currentUser = userContextProvider.getCurrentUser()
        val expense = expenseRepository.get(id) ?: throw NotFoundException("Expense with id $id does not exist")
        val project = projectRepository.get(expense.projectId)
            ?: throw IllegalStateException("Project with id ${expense.projectId} does not exist")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} tried to delete expense ${expense.id} for project ${project.id} without being a project member")
            throw ProjectAccessException("User ${currentUser.id} tried to delete expense ${expense.id} for project ${project.id} without being a project member")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) && currentUser.id != expense.spender.id) {
            logger.error("User ${currentUser.id} tried to delete expense ${expense.id} for project ${project.id} for another user without being a project manager")
            throw ExpenseAccessException("User ${currentUser.id} tried to delete expense ${expense.id} for project ${project.id} for another user without being a project manager")
        }

        expenseRepository.delete(id)
    }
}
