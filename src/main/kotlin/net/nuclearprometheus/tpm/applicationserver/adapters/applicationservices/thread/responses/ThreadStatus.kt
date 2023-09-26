package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadStatus

data class ThreadStatus(
    val status: ThreadStatus,
    val title: String,
    val description: String
)
