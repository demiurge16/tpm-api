package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.task.TimeEntryValidationException
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.LocalDate

class TimeEntry(
    id: TimeEntryId = TimeEntryId(),
    user: User,
    date: LocalDate,
    timeSpent: Int,
    timeUnit: TimeUnit,
    status: TimeEntryStatus = TimeEntryStatus.DRAFT,
    description: String,
    taskId: TaskId
) : Entity<TimeEntryId>(id) {

    var user = user; private set
    var date = date; private set
    var timeSpent = timeSpent; private set
    var timeUnit = timeUnit; private set
    var status = status; private set
    var description = description; private set
    var taskId = taskId; private set

    init {
        validateTimeSpent()
    }

    private fun validateTimeSpent() {
        if (timeSpent < 0) {
            throw TimeEntryValidationException("Time spent cannot be negative")
        }

        if (timeSpent * timeUnit.multiplier > 1440) {
            throw TimeEntryValidationException("Time spent cannot be more than ${1440 / timeUnit.multiplier} ${timeUnit.title}")
        }
    }

    fun update(
        user: User,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String
    ) {
        if (status != TimeEntryStatus.DRAFT) {
            throw TimeEntryValidationException("Only draft time entries can be updated")
        }

        this.user = user
        this.date = date
        this.timeSpent = timeSpent
        this.timeUnit = timeUnit
        this.description = description
    }

    fun submit() {
        if (status != TimeEntryStatus.DRAFT) {
            throw TimeEntryValidationException("Only draft time entries can be submitted")
        }

        status = TimeEntryStatus.SUBMITTED
    }

    fun approve() {
        if (status != TimeEntryStatus.SUBMITTED) {
            throw TimeEntryValidationException("Only submitted time entries can be approved")
        }

        status = TimeEntryStatus.APPROVED
    }

    fun reject() {
        if (status != TimeEntryStatus.SUBMITTED) {
            throw TimeEntryValidationException("Only submitted time entries can be rejected")
        }

        status = TimeEntryStatus.REJECTED
    }
}
