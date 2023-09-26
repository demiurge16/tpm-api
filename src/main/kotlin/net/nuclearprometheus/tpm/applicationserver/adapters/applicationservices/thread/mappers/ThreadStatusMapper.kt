package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadNewStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread

object ThreadStatusMapper {

    fun Thread.toThreadStatusResponse() = ThreadNewStatus(
        id = id.value,
        status = ThreadStatus(
            status = status,
            title = status.title,
            description = status.description
        )
    )
}
