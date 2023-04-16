package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense

object ExpenseMapper {

    fun Expense.toView() = ExpenseResponse.View(
        id = id.value,
        description = description,
        category = ExpenseResponse.View.ExpenseCategoryView(
            id = category.id.value,
            name = category.name,
            description = category.description
        ),
        amount = amount,
        currency = ExpenseResponse.View.CurrencyView(
            code = currency.id.value,
            name = currency.name
        ),
        date = date,
        teamMemberId = teamMemberId.value,
        projectId = projectId.value
    )
}