package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread

sealed class ThreadRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Thread>( page, size, sort, search) {

        override fun toString(): String {
            return "ThreadRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }

    data class Update(
        val title: String,
        val content: String,
        val tags: kotlin.collections.List<String>
    ) : ThreadRequest()
}