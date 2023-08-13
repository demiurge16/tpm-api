package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadStatus

data class Status(
    val status: ThreadStatus,
    val title: String,
    val description: String
)
