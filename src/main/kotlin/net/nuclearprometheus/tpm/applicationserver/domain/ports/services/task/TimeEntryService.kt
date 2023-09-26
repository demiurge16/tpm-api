package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeUnit
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import java.time.LocalDate

interface TimeEntryService {
    fun create(
        userId: UserId,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String,
        taskId: TaskId
    ): TimeEntry

    fun createSubmitted(
        userId: UserId,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String,
        taskId: TaskId
    ): TimeEntry

    fun update(
        id: TimeEntryId,
        userId: UserId,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String
    ): TimeEntry

    fun delete(id: TimeEntryId)
    fun submit(id: TimeEntryId): TimeEntry
    fun approve(id: TimeEntryId): TimeEntry
    fun reject(id: TimeEntryId): TimeEntry
}
