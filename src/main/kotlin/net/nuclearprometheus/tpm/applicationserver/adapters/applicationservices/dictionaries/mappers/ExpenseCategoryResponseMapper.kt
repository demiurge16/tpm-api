package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExpenseCategoryStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExpenseCategory as ExpenseCategoryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory

object ExpenseCategoryResponseMapper {
    fun ExpenseCategory.toView() = ExpenseCategoryResponse(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun ExpenseCategory.toActivityStatus() = ExpenseCategoryStatus(id = id.value, active = active)
}
