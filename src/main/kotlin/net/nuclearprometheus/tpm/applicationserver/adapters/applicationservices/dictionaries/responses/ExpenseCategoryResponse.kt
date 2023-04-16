package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class ExpenseCategoryResponse {
    data class View(
        val id: UUID,
        val name: String,
        val description: String,
        val activityStatus: ActivityStatus
    ) : ExpenseCategoryResponse()

    data class ActivityStatus(val id: UUID, val active: Boolean) : ExpenseCategoryResponse()
}