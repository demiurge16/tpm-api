package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

class ListProjects(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Project>( page, size, sort, search) {

    override fun toString(): String {
        return "ProjectRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}

