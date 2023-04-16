package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExpenseCategoryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory

object ExpenseCategoryResponseMapper {
    fun ExpenseCategory.toView() = ExpenseCategoryResponse.View(
        id = id.value,
        name = name,
        description = description,
        activityStatus = toActivityStatus()
    )

    fun ExpenseCategory.toActivityStatus() = ExpenseCategoryResponse.ActivityStatus(id = id.value, active = active)
}
