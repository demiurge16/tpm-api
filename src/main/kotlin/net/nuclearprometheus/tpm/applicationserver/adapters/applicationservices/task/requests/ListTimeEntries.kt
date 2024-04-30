package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry

class ListTimeEntries(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<TimeEntry>( page, size, sort, search) {

    override fun toString(): String {
        return "ListTimeEntries(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
