package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority

sealed class PriorityRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Priority>( page, size, sort, search) {

        override fun toString(): String {
            return "PriorityRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }

    data class Create(
        val name: String,
        val description: String,
        val emoji: String,
        val value: Int
    ) : PriorityRequest()

    data class Update(
        val name: String,
        val description: String,
        val emoji: String,
        val value: Int
    ) : PriorityRequest()
}