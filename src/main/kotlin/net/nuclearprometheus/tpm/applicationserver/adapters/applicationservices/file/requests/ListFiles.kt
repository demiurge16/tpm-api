package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File

class ListFiles(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<File>( page, size, sort, search) {

    override fun toString(): String {
        return "FileRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}
