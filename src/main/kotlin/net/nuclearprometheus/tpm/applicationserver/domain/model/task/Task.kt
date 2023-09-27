package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.task.TaskStatusChangeException
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.math.BigDecimal
import java.time.ZonedDateTime

class Task(
    id: TaskId = TaskId(),
    title: String,
    description: String,
    sourceLanguage: Language,
    targetLanguage: Language,
    accuracy: Accuracy,
    industry: Industry,
    unit: Unit,
    serviceType: ServiceType,
    amount: Int,
    expectedStart: ZonedDateTime,
    deadline: ZonedDateTime,
    budget: BigDecimal,
    currency: Currency,
    status: TaskStatus = TaskStatus.DRAFT,
    priority: Priority,
    assignee: User? = null,
    projectId: ProjectId,
    timeEntries: List<TimeEntry> = mutableListOf(),
) : Entity<TaskId>(id) {

    var title = title; private set
    var description = description; private set
    var sourceLanguage = sourceLanguage; private set
    var targetLanguage = targetLanguage; private set
    var accuracy = accuracy; private set
    var industry = industry; private set
    var unit = unit; private set
    var serviceType = serviceType; private set
    var amount = amount; private set
    var expectedStart = expectedStart; private set
    var deadline = deadline; private set
    var budget = budget; private set
    var currency = currency; private set
    var status = status; private set
    var priority = priority; private set
    var assignee = assignee; private set
    var projectId = projectId; private set
    var timeEntries = timeEntries; private set

    fun update(
        title: String,
        description: String,
        sourceLanguage: Language,
        targetLanguage: Language,
        accuracy: Accuracy,
        industry: Industry,
        unit: Unit,
        serviceType: ServiceType,
        amount: Int,
        budget: BigDecimal,
        currency: Currency,
    ) {
        this.title = title
        this.description = description
        this.sourceLanguage = sourceLanguage
        this.targetLanguage = targetLanguage
        this.accuracy = accuracy
        this.industry = industry
        this.unit = unit
        this.serviceType = serviceType
        this.amount = amount
        this.budget = budget
        this.currency = currency
    }

    fun moveStart(expectedStart: ZonedDateTime) {
        this.expectedStart = expectedStart
    }

    fun moveDeadline(deadline: ZonedDateTime) {
        this.deadline = deadline
    }

    fun changePriority(priority: Priority) {
        this.priority = priority
    }

    fun assign(assignee: User) {
        this.assignee = assignee

        if (status == TaskStatus.DRAFT) {
            status = TaskStatus.ASSIGNED
        }
    }

    fun unassign() {
        this.assignee = null
        status = TaskStatus.DRAFT
    }

    fun start() {
        if (status != TaskStatus.ASSIGNED) {
            throw TaskStatusChangeException("Task must be assigned to someone to start")
        }
        status = TaskStatus.IN_PROGRESS
    }

    fun startReview() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw TaskStatusChangeException("Task must be in progress to complete")
        }
        status = TaskStatus.IN_REVIEW
    }

    fun reject() {
        if (status != TaskStatus.IN_REVIEW) {
            throw TaskStatusChangeException("Task must be in needs review to request revisions")
        }
        status = TaskStatus.IN_PROGRESS
    }

    fun approve() {
        if (status != TaskStatus.IN_REVIEW) {
            throw TaskStatusChangeException("Task must be in review to complete revisions")
        }
        status = TaskStatus.READY_TO_DELIVER
    }

    fun complete() {
        if (status != TaskStatus.READY_TO_DELIVER) {
            throw TaskStatusChangeException("Task must be in needs review to deliver")
        }
        status = TaskStatus.COMPLETED
    }

    fun putOnHold() {
        if (status !in listOf(TaskStatus.ASSIGNED, TaskStatus.IN_PROGRESS)) {
            throw TaskStatusChangeException("Task must be in progress to put on hold")
        }
        status = TaskStatus.ON_HOLD
    }

    fun resume() {
        if (status != TaskStatus.ON_HOLD) {
            throw TaskStatusChangeException("Task must be on hold to resume")
        }
        status = TaskStatus.IN_PROGRESS
    }

    fun cancel() {
        if (status == TaskStatus.COMPLETED) {
            throw TaskStatusChangeException("Task cannot be cancelled once it has been completed")
        }
        status = TaskStatus.CANCELLED
        assignee = null
    }

    fun reopen() {
        if (status != TaskStatus.CANCELLED) {
            throw TaskStatusChangeException("Task can only be reopened if it has been cancelled")
        }
        status = TaskStatus.DRAFT
        assignee = null
    }
}

