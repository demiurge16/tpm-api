package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object ProjectExpenseMapper {

    fun Expense.toView(spender: User, project: Project) = ExpenseResponse.Expense(
        id = id.value,
        description = description,
        category = ExpenseCategory(
            id = category.id.value,
            name = category.name,
            description = category.description
        ),
        amount = amount,
        currency = Currency(
            code = currency.id.value,
            name = currency.name
        ),
        date = date,
        teamMember = Spender(
            userId = spender.id.value,
            firstName = spender.firstName,
            lastName = spender.lastName,
            email = spender.email
        ),
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = ProjectStatus(
                status = project.status,
                title = project.status.title,
                description = project.status.description
            )
        )
    )
}