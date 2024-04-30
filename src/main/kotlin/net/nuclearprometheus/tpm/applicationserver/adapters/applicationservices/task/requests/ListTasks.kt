package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

class ListTasks(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Task>( page, size, sort, search) {

    override fun toString(): String {
        return "ListTasks(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
