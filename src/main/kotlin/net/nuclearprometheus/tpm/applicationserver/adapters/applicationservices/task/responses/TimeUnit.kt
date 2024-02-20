package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeUnit

data class TimeUnit(
    val unit: TimeUnit,
    val title: String,
    val description: String
)
