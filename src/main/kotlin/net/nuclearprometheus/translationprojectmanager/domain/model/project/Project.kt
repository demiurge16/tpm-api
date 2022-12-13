package net.nuclearprometheus.translationprojectmanager.domain.model.project

import net.nuclearprometheus.translationprojectmanager.domain.exceptions.project.ProjectStatusChangeException
import net.nuclearprometheus.translationprojectmanager.domain.exceptions.project.ProjectValidationException
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientId
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Industry
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Language
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.Currency
import java.util.UUID

data class UserId(val value: UUID = UUID.randomUUID())

/**
 * Translation project model
 * @param id project id
 * @param title project title
 * @param description project description
 * @param sourceLanguage source language
 * @param targetLanguages target languages
 * @param accuracy expected translation accuracy
 * @param industry which industry does translation project belong to
 * @param unit unit of translation
 * @param amount amount of translation
 * @param expectedStart expected start
 * @param internalDeadline internal deadline
 * @param externalDeadline external deadline
 * @param budget budget
 * @param currency currency
 * @param status status
 * @param managerId manager id
 * @param clientId client id
 */
class Project(
    id: ProjectId = ProjectId(),
    title: String,
    description: String,
    sourceLanguage: Language,
    targetLanguages: List<Language>,
    accuracy: Accuracy,
    industry: Industry,
    unit: Unit,
    amount: Int,
    expectedStart: ZonedDateTime,
    internalDeadline: ZonedDateTime,
    externalDeadline: ZonedDateTime,
    budget: BigDecimal,
    currency: Currency,
    status: ProjectStatus = ProjectStatus.DRAFT,
    managerId: UserId,
    clientId: ClientId
) {
    var id = id; private set
    var title = title; private set
    var description = description; private set
    var sourceLanguage = sourceLanguage; private set
    var targetLanguages = targetLanguages; private set
    var accuracy = accuracy; private set
    var industry = industry; private set
    var unit = unit; private set
    var amount = amount; private set
    var expectedStart = expectedStart; private set
    var internalDeadline = internalDeadline; private set
    var externalDeadline = externalDeadline; private set
    var budget = budget; private set
    var currency = currency; private set
    var status = status; private set
    var managerId = managerId; private set
    var clientId = clientId; private set

    init {
        validateTitle()
        validateDates()
        validateBudget()
        validateAmount()
    }

    private fun validateTitle() {
        if (title.isBlank()) {
            throw IllegalArgumentException("Title cannot be blank")
        }
    }

    private fun validateDates() {
        if (expectedStart.isAfter(internalDeadline)) {
            throw ProjectValidationException("Expected start date must be before internal deadline")
        }
        if (internalDeadline.isAfter(externalDeadline)) {
            throw ProjectValidationException("Internal deadline must be before external deadline")
        }
    }

    private fun validateAmount() {
        if (amount <= 0) {
            throw ProjectValidationException("Amount must be greater than 0")
        }
    }

    private fun validateBudget() {
        if (budget <= BigDecimal.ZERO) {
            throw ProjectValidationException("Budget must be greater than 0")
        }
    }

    fun update(
        title: String,
        description: String,
        sourceLanguage: Language,
        targetLanguages: List<Language>,
        accuracy: Accuracy,
        industry: Industry,
        unit: Unit,
        amount: Int,
        budget: BigDecimal,
        currency: Currency,
        managerId: UserId,
        clientId: ClientId
    ) {
        this.title = title
        this.description = description
        this.sourceLanguage = sourceLanguage
        this.targetLanguages = targetLanguages
        this.accuracy = accuracy
        this.industry = industry
        this.unit = unit
        this.amount = amount
        this.budget = budget
        this.currency = currency
        this.managerId = managerId
        this.clientId = clientId

        validateTitle()
        validateAmount()
        validateBudget()
    }

    fun moveStart(expectedStart: ZonedDateTime) {
        this.expectedStart = expectedStart

        validateDates()
    }

    fun moveDeadlines(internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime) {
        this.internalDeadline = internalDeadline
        this.externalDeadline = externalDeadline

        validateDates()
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
        status = ProjectStatus.IN_PROGRESS
    }

    fun finishProgress() {
        if (status != ProjectStatus.IN_PROGRESS) {
            throw ProjectStatusChangeException("Project must be in progress status")
        }
        status = ProjectStatus.READY_TO_DELIVER
    }

    fun backToProgress() {
        if (status != ProjectStatus.READY_TO_DELIVER) {
            throw ProjectStatusChangeException("Project must be in ready to deliver status")
        }
        status = ProjectStatus.IN_PROGRESS
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

    fun cancel() {
        if (status !in listOf(
                ProjectStatus.DRAFT,
                ProjectStatus.READY_TO_START,
                ProjectStatus.IN_PROGRESS,
                ProjectStatus.READY_TO_DELIVER
            )
        ) {
            throw ProjectStatusChangeException("Project must be in draft, ready to start, in progress or ready to deliver status")
        }
        status = ProjectStatus.CANCELLED
    }
}
