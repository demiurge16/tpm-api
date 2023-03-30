package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.task.TaskValidationException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import java.math.BigDecimal
import java.time.ZonedDateTime

class Task(
    val id: TaskId = TaskId(),
    title: String,
    description: String,
    sourceLanguage: Language,
    targetLanguage: Language,
    accuracy: Accuracy,
    industry: Industry,
    unit: Unit,
    amount: Int,
    expectedStart: ZonedDateTime,
    deadline: ZonedDateTime,
    budget: BigDecimal,
    currency: Currency,
    assignee: TeamMember? = null,
    status: TaskStatus = TaskStatus.DRAFT,
    projectId: ProjectId
) {
    var title = title; private set
    var description = description; private set
    var sourceLanguage = sourceLanguage; private set
    var targetLanguage = targetLanguage; private set
    var accuracy = accuracy; private set
    var industry = industry; private set
    var unit = unit; private set
    var amount = amount; private set
    var expectedStart = expectedStart; private set
    var deadline = deadline; private set
    var budget = budget; private set
    var currency = currency; private set
    var assignee = assignee; private set
    var status = status; private set
    var projectId = projectId; private set

    init {
        validateTitle()
        validateDates()
        validateAmount()
        validateBudget()
    }

    private fun validateTitle() {
        if (title.isBlank()) {
            throw TaskValidationException("Title cannot be blank")
        }
    }

    private fun validateDates() {
        if (deadline.isBefore(expectedStart)) {
            throw TaskValidationException("Deadline cannot be before expected start")
        }
    }

    private fun validateAmount() {
        if (amount <= 0) {
            throw TaskValidationException("Amount must be greater than 0")
        }
    }

    private fun validateBudget() {
        if (budget <= BigDecimal.ZERO) {
            throw TaskValidationException("Budget must be greater than 0")
        }
    }

    fun update(
        title: String,
        description: String,
        sourceLanguage: Language,
        targetLanguage: Language,
        accuracy: Accuracy,
        industry: Industry,
        unit: Unit,
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
        this.amount = amount
        this.budget = budget
        this.currency = currency

        validateTitle()
        validateAmount()
        validateBudget()
    }

    fun moveStart(expectedStart: ZonedDateTime) {
        this.expectedStart = expectedStart
        validateDates()
    }

    fun moveDeadline(deadline: ZonedDateTime) {
        this.deadline = deadline
        validateDates()
    }

    fun assignTeamMember(assignee: TeamMember) {
        this.assignee = assignee

        if (status == TaskStatus.DRAFT) {
            status = TaskStatus.ASSIGNED
        }
    }

    fun unassignTeamMember() {
        this.assignee = null
        status = TaskStatus.DRAFT
    }

    fun start() {
        if (status != TaskStatus.ASSIGNED) {
            throw TaskValidationException("Task must be assigned to someone to start")
        }
        status = TaskStatus.IN_PROGRESS
    }

    fun complete() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw TaskValidationException("Task must be in progress to complete")
        }
        status = TaskStatus.NEEDS_REVIEW
    }

    fun requestRevisions() {
        if (status != TaskStatus.NEEDS_REVIEW) {
            throw TaskValidationException("Task must be in needs review to request revisions")
        }
        status = TaskStatus.REVISIONS_NEEDED
    }

    fun completeRevisions() {
        if (status != TaskStatus.REVISIONS_NEEDED) {
            throw TaskValidationException("Task must be in revisions needed to complete revisions")
        }
        status = TaskStatus.NEEDS_REVIEW
    }

    fun deliver() {
        if (status != TaskStatus.NEEDS_REVIEW) {
            throw TaskValidationException("Task must be in needs review to deliver")
        }
        status = TaskStatus.COMPLETED
    }

    fun cancel() {
        if (status == TaskStatus.COMPLETED) {
            throw TaskValidationException("Task cannot be cancelled once it has been completed")
        }
        status = TaskStatus.CANCELLED
    }

    fun reopen() {
        if (status != TaskStatus.CANCELLED) {
            throw TaskValidationException("Task can only be reopened if it has been cancelled")
        }
        status = TaskStatus.DRAFT
    }
}
