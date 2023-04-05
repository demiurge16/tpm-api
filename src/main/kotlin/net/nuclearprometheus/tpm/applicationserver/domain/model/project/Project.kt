package net.nuclearprometheus.tpm.applicationserver.domain.model.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectStatusChangeException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectValidationException
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
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
    notes: List<Note> = mutableListOf(),
    files: List<File> = mutableListOf(),
    chats: List<Chat> = mutableListOf(),
    client: Client
) : Entity<ProjectId>(id) {

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
    var teamMembers = teamMembers; private set
    var tasks = tasks; private set
    var expenses = expenses; private set
    var notes = notes; private set
    var files = files; private set
    var chats = chats; private set
    var client = client; private set

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
        client: Client
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
        this.client = client

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

    fun addTeamMember(teamMember: TeamMember) {
        teamMembers = teamMembers.plus(teamMember)
    }

    fun removeTeamMember(teamMember: TeamMember) {
        teamMembers = teamMembers.minus(teamMember)
    }

    fun addTask(task: Task) {
        tasks = tasks.plus(task)
    }

    fun removeTask(task: Task) {
        tasks = tasks.minus(task)
    }

    fun addExpense(expense: Expense) {
        expenses = expenses.plus(expense)
    }

    fun removeExpense(expense: Expense) {
        expenses = expenses.minus(expense)
    }

    fun addNote(note: Note) {
        notes = notes.plus(note)
    }

    fun addFile(file: File) {
        files = files.plus(file)
    }

    fun addChat(chat: Chat) {
        chats = chats.plus(chat)
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

    fun finishProgress() {
        if (status != ProjectStatus.ACTIVE) {
            throw ProjectStatusChangeException("Project must be in progress status")
        }
        status = ProjectStatus.READY_TO_DELIVER
    }

    fun backToProgress() {
        if (status != ProjectStatus.READY_TO_DELIVER) {
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
        if (status !in listOf(
                ProjectStatus.DRAFT,
                ProjectStatus.READY_TO_START,
                ProjectStatus.ACTIVE,
                ProjectStatus.READY_TO_DELIVER
            )
        ) {
            throw ProjectStatusChangeException("Project must be in draft, ready to start, in progress or ready to deliver status")
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
        if (status !in listOf(
                ProjectStatus.DRAFT,
                ProjectStatus.READY_TO_START,
                ProjectStatus.ACTIVE,
                ProjectStatus.READY_TO_DELIVER
            )
        ) {
            throw ProjectStatusChangeException("Project must be in draft, ready to start, in progress or ready to deliver status")
        }
        status = ProjectStatus.CANCELLED
    }

    fun reopen() {
        if (status != ProjectStatus.CANCELLED) {
            throw ProjectStatusChangeException("Project must be in cancelled status")
        }
        status = ProjectStatus.DRAFT
    }
}
