package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeUnit
import java.time.LocalDate
import java.util.*

data class UpdateTimeEntry(
    val userId: UUID,
    val date: LocalDate,
    val timeSpent: Int,
    val timeUnit: TimeUnit,
    val description: String
)
