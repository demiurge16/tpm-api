package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import java.util.*

sealed class ThreadStatusResponse {

    data class NewStatus(
        val id: UUID,
        val status: Status
    ) : ThreadStatusResponse()
}
