package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.CurrencyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ExpenseCategoryResponseMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses.Expense as ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object ExpenseMapper {

    fun Expense.toView(spender: User, project: Project) = ExpenseResponse(
        id = id.value,
        description = description,
        category = category.toView(),
        amount = amount,
        currency = currency.toView(),
        date = date,
        teamMember = spender.toView(),
        project = project.toShortView()
    )
}
