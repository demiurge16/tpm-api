package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.task.TaskStatusChangeException
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate
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

    init {
        validate {
            assert { title.isNotBlank() } otherwise {
                ValidationError("title", "Title cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { accuracy.active } otherwise {
                ValidationError("accuracy", "Accuracy cannot be inactive")
            }
            assert { industry.active } otherwise {
                ValidationError("industry", "Industry cannot be inactive")
            }
            assert { unit.active } otherwise {
                ValidationError("unit", "Unit cannot be inactive")
            }
            assert { serviceType.active } otherwise {
                ValidationError("serviceType", "Service type cannot be inactive")
            }
            assert { amount > 0 } otherwise {
                ValidationError("amount", "Amount must be greater than zero")
            }
            assert { budget > BigDecimal.ZERO } otherwise {
                ValidationError("budget", "Budget must be greater than zero")
            }
            assert { priority.active } otherwise {
                ValidationError("priority", "Priority cannot be inactive")
            }
            assert { expectedStart.isBefore(deadline) } otherwise {
                ValidationError("expectedStart", "Expected start must be before deadline")
            }
        }
    }

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
        val accuracyChanged = { this.accuracy.id != accuracy.id }
        val industryChanged = { this.industry.id != industry.id }
        val unitChanged = { this.unit.id != unit.id }
        val serviceTypeChanged = { this.serviceType.id != serviceType.id }

        validate {
            assert { title.isNotBlank() } otherwise {
                ValidationError("title", "Title cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { accuracy.active } otherwise {
                ValidationError("accuracy", "Accuracy cannot be inactive")
            }
            assert { industry.active } otherwise {
                ValidationError("industry", "Industry cannot be inactive")
            }
            assert { unit.active } otherwise {
                ValidationError("unit", "Unit cannot be inactive")
            }
            assert { serviceType.active } otherwise {
                ValidationError("serviceType", "Service type cannot be inactive")
            }
            assert { amount > 0 } otherwise {
                ValidationError("amount", "Amount must be greater than zero")
            }
            assert { budget > BigDecimal.ZERO } otherwise {
                ValidationError("budget", "Budget must be greater than zero")
            }
        }

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
        validate {
            assert { expectedStart.isBefore(deadline) } otherwise {
                ValidationError("expectedStart", "Expected start must be before deadline")
            }
        }

        this.expectedStart = expectedStart
    }

    fun moveDeadline(deadline: ZonedDateTime) {
        validate {
            assert { expectedStart.isBefore(deadline) } otherwise {
                ValidationError("expectedStart", "Expected start must be before deadline")
            }
        }

        this.deadline = deadline
    }

    fun changePriority(priority: Priority) {
        validate {
            assert { priority.active } otherwise {
                ValidationError("priority", "Priority cannot be inactive")
            }
        }

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

