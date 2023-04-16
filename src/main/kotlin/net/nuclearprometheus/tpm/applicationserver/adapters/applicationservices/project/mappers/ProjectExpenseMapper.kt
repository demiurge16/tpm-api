package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense

object ProjectExpenseMapper {

    fun Expense.toView() = ProjectExpenseResponse.View(
        id = id.value,
        description = description,
        category = ProjectExpenseResponse.View.ExpenseCategoryView(
            id = category.id.value,
            name = category.name,
            description = category.description
        ),
        amount = amount,
        currency = ProjectExpenseResponse.View.CurrencyView(
            code = currency.id.value,
            name = currency.name
        ),
        date = date,
        teamMemberId = teamMemberId.value,
        projectId = projectId.value
    )
}