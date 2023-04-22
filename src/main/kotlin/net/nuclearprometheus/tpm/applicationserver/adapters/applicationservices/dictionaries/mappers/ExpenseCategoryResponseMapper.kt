package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExpenseCategoryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory

object ExpenseCategoryResponseMapper {
    fun ExpenseCategory.toView() = ExpenseCategoryResponse.ExpenseCategory(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun ExpenseCategory.toActivityStatus() = ExpenseCategoryResponse.Status(id = id.value, active = active)
}
