package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File

sealed class FileRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<File>( page, size, sort, search) {

        override fun toString(): String {
            return "FileRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }
}
