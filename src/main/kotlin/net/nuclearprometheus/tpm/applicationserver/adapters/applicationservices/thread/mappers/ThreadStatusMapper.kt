package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Status
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread

object ThreadStatusMapper {

    fun Thread.toThreadStatusResponse() = ThreadStatusResponse.NewStatus(
        id = id.value,
        status = Status(
            status = status,
            title = status.title,
            description = status.description
        )
    )
}
