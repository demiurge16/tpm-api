package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.time.LocalDate
import java.util.*

data class TimeEntry(
    val id: UUID,
    val user: User,
    val date: LocalDate,
    val timeSpent: Int,
    val timeUnit: TimeUnit,
    val status: TimeEntryStatus,
    val description: String,
    val taskId: UUID
)
