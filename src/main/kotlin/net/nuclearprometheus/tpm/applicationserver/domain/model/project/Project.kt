package net.nuclearprometheus.tpm.applicationserver.domain.model.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectStatusChangeException
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate
import java.math.BigDecimal
import java.time.ZonedDateTime

class Project(
    id: ProjectId = ProjectId(),
    title: String,
    description: String,
    sourceLanguage: Language,
    targetLanguages: List<Language>,
    accuracy: Accuracy,
    industry: Industry,
    unit: Unit,
    serviceTypes: List<ServiceType>,
    amount: Int,
    expectedStart: ZonedDateTime,
    internalDeadline: ZonedDateTime,
    externalDeadline: ZonedDateTime,
    budget: BigDecimal,
    currency: Currency,
    status: ProjectStatus = ProjectStatus.DRAFT,
    teamMembers: List<TeamMember> = mutableListOf(),
    tasks: List<Task> = mutableListOf(),
    expenses: List<Expense> = mutableListOf(),
    files: List<File> = mutableListOf(),
    threads: List<Thread> = mutableListOf(),
    client: Client
) : Entity<ProjectId>(id) {

    init {
        validate {
            assert { title.isNotBlank() } otherwise {
                ValidationError("title", "Title cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { targetLanguages.isNotEmpty() } otherwise {
                ValidationError("targetLanguages", "Target languages cannot be empty")
            }
            assert { accuracy.active } otherwise {
                ValidationError("accuracy", "Accuracy must be active")
            }
            assert { industry.active } otherwise {
                ValidationError("industry", "Industry must be active")
            }
            assert { unit.active } otherwise {
                ValidationError("unit", "Unit must be active")
            }
            assert { serviceTypes.isNotEmpty() } otherwise {
                ValidationError("serviceTypes", "Service types cannot be empty")
            }
            assert { serviceTypes.all { it.active } } otherwise {
                ValidationError("serviceTypes", "All service types must be active")
            }
            assert { amount > 0 } otherwise {
                ValidationError("amount", "Amount must be greater than zero")
            }
            assert { budget > BigDecimal.ZERO } otherwise {
                ValidationError("budget", "Budget must be greater than zero")
            }
            assert { internalDeadline.isAfter(expectedStart) } otherwise {
                ValidationError("internalDeadline", "Internal deadline must be after expected start")
            }
            assert { externalDeadline.isAfter(internalDeadline) } otherwise {
                ValidationError("externalDeadline", "External deadline must be after internal deadline")
            }
            assert { client.active } otherwise {
                ValidationError("client", "Client must be active")
            }
        }
    }

    var title = title; private set
    var description = description; private set
    var sourceLanguage = sourceLanguage; private set
    var targetLanguages = targetLanguages; private set
    var accuracy = accuracy; private set
    var industry = industry; private set
    var unit = unit; private set
    var serviceTypes = serviceTypes; private set
    var amount = amount; private set
    var expectedStart = expectedStart; private set
    var internalDeadline = internalDeadline; private set
    var externalDeadline = externalDeadline; private set
    var budget = budget; private set
    var currency = currency; private set
    var status = status; private set
    var teamMembers = teamMembers; private set
    var tasks = tasks; private set
    var expenses = expenses; private set
    var files = files; private set
    var threads = threads; private set
    var client = client; private set

    fun update(
        title: String,
        description: String,
        sourceLanguage: Language,
        targetLanguages: List<Language>,
        accuracy: Accuracy,
        industry: Industry,
        unit: Unit,
        serviceTypes: List<ServiceType>,
        amount: Int,
        budget: BigDecimal,
        currency: Currency,
        client: Client
    ) {
        val accuracyChanged = { this.accuracy.id != accuracy.id }
        val industryChanged = { this.industry.id != industry.id }
        val unitChanged = { this.unit.id != unit.id }
        val serviceTypesChanged = { this.serviceTypes.map { it.id } != serviceTypes.map { it.id } }
        val clientChanged = { this.client.id != client.id }

        validate {
            assert { title.isNotBlank() } otherwise {
                ValidationError("title", "Title cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { targetLanguages.isNotEmpty() } otherwise {
                ValidationError("targetLanguages", "Target languages cannot be empty")
            }
            assertIf(accuracyChanged) { accuracy.active } otherwise {
                ValidationError("accuracy", "Accuracy must be active")
            }
            assertIf(industryChanged) { industry.active } otherwise {
                ValidationError("industry", "Industry must be active")
            }
            assertIf(unitChanged) { unit.active } otherwise {
                ValidationError("unit", "Unit must be active")
            }
            assertIf(serviceTypesChanged) { serviceTypes.isNotEmpty() } otherwise {
                ValidationError("serviceTypes", "Service types cannot be empty")
            }
            assertIf(serviceTypesChanged) { serviceTypes.all { it.active } } otherwise {
                ValidationError("serviceTypes", "All service types must be active")
            }
            assert { amount > 0 } otherwise {
                ValidationError("amount", "Amount must be greater than zero")
            }
            assert { budget > BigDecimal.ZERO } otherwise {
                ValidationError("budget", "Budget must be greater than zero")
            }
            assertIf(clientChanged) { client.active } otherwise {
                ValidationError("client", "Client must be active")
            }
        }

        this.title = title
        this.description = description
        this.sourceLanguage = sourceLanguage
        this.targetLanguages = targetLanguages
        this.accuracy = accuracy
        this.industry = industry
        this.unit = unit
        this.serviceTypes = serviceTypes
        this.amount = amount
        this.budget = budget
        this.currency = currency
        this.client = client
    }

    fun moveStart(expectedStart: ZonedDateTime) {
        validate {
            assert { expectedStart.isBefore(internalDeadline) } otherwise {
                ValidationError("expectedStart", "Expected start must be in the future")
            }
        }

        this.expectedStart = expectedStart
    }

    fun moveDeadlines(internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime) {
        validate {
            assert { internalDeadline.isAfter(expectedStart) } otherwise {
                ValidationError("internalDeadline", "Internal deadline must be after expected start")
            }
            assert { externalDeadline.isAfter(internalDeadline) } otherwise {
                ValidationError("externalDeadline", "External deadline must be after internal deadline")
            }
        }

        this.internalDeadline = internalDeadline
        this.externalDeadline = externalDeadline
    }

    fun finishDraft() {
        if (status != ProjectStatus.DRAFT) {
            throw ProjectStatusChangeException("Project must be in draft status")
        }
        status = ProjectStatus.READY_TO_START
    }

    fun backToDraft() {
        if (status != ProjectStatus.READY_TO_START) {
            throw ProjectStatusChangeException("Project must be in ready to start status")
        }
        status = ProjectStatus.DRAFT
    }

    fun startProgress() {
        if (status != ProjectStatus.READY_TO_START) {
            throw ProjectStatusChangeException("Project must be in ready to start status")
        }
        status = ProjectStatus.ACTIVE
    }

    fun startReview() {
        if (status != ProjectStatus.ACTIVE) {
            throw ProjectStatusChangeException("Project must be in progress status")
        }
        status = ProjectStatus.REVIEW
    }

    fun approve() {
        if (status != ProjectStatus.REVIEW) {
            throw ProjectStatusChangeException("Project must be in review status")
        }
        status = ProjectStatus.READY_TO_DELIVER
    }

    fun reject() {
        if (status != ProjectStatus.REVIEW) {
            throw ProjectStatusChangeException("Project must be in ready to deliver status")
        }
        status = ProjectStatus.ACTIVE
    }

    fun deliver() {
        if (status != ProjectStatus.READY_TO_DELIVER) {
            throw ProjectStatusChangeException("Project must be in ready to deliver status")
        }
        status = ProjectStatus.DELIVERED
    }

    fun invoice() {
        if (status != ProjectStatus.DELIVERED) {
            throw ProjectStatusChangeException("Project must be in delivered status")
        }
        status = ProjectStatus.INVOICED
    }

    fun pay() {
        if (status != ProjectStatus.INVOICED) {
            throw ProjectStatusChangeException("Project must be in invoiced status")
        }
        status = ProjectStatus.PAID
    }

    fun putOnHold() {
        if (
            status !in listOf(
                ProjectStatus.READY_TO_START,
                ProjectStatus.ACTIVE,
                ProjectStatus.REVIEW,
                ProjectStatus.READY_TO_DELIVER
            )
        ) {
            throw ProjectStatusChangeException("Project must be in ready to start, in progress or ready to deliver status")
        }
        status = ProjectStatus.ON_HOLD
    }

    fun resume() {
        if (status != ProjectStatus.ON_HOLD) {
            throw ProjectStatusChangeException("Project must be in on hold status")
        }
        status = ProjectStatus.ACTIVE
    }

    fun cancel() {
        if (
            status !in listOf(
                ProjectStatus.DRAFT,
                ProjectStatus.READY_TO_START,
                ProjectStatus.ACTIVE,
                ProjectStatus.ON_HOLD,
                ProjectStatus.REVIEW,
                ProjectStatus.READY_TO_DELIVER
            )
        ) {
            throw ProjectStatusChangeException("Project must be in draft, ready to start, on hold, active or ready to deliver status")
        }
        status = ProjectStatus.CANCELLED
    }

    fun reopen() {
        if (status != ProjectStatus.CANCELLED) {
            throw ProjectStatusChangeException("Project must be in cancelled status")
        }
        status = ProjectStatus.DRAFT
    }

    fun hasTeamMember(userId: UserId): Boolean {
        return teamMembers.any { it.user.id == userId }
    }

    fun hasTeamMemberWithRole(userId: UserId, role: ProjectRole): Boolean {
        return teamMembers.any { it.user.id == userId && it.hasRole(role) }
    }

    fun hasTeamMemberWithAnyRole(userId: UserId, roles: List<ProjectRole>): Boolean {
        return teamMembers.any { it.user.id == userId && it.hasAnyRole(roles) }
    }
}
