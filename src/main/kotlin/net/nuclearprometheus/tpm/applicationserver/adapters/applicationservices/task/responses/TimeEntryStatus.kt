package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryStatus

data class TimeEntryStatus(
    val status: TimeEntryStatus,
    val title: String,
    val description: String
)
