package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

class ListUsers(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<User>( page, size, sort, search) {

    override fun toString(): String {
        return "UserRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}