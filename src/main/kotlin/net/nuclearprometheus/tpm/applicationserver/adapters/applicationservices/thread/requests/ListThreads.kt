package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread

class ListThreads(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Thread>( page, size, sort, search) {

    override fun toString(): String {
        return "ThreadRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
