package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.Currency
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense

object ProjectExpenseMapper {

    fun Expense.toView() = ExpenseResponse.Expense(
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
        teamMemberId = teamMemberId.value,
        projectId = projectId.value
    )
}