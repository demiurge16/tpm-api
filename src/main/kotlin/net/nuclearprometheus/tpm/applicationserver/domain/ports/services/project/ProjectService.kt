package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import liquibase.hub.model.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import java.math.BigDecimal
import java.time.ZonedDateTime

interface ProjectService {
    fun getAll(): List<Project>
    fun get(id: ProjectId): Project?
    fun create(
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguages: List<LanguageCode>,
        accuracyId: AccuracyId,
        industryId: IndustryId,
        unitId: UnitId,
        amount: Int,
        expectedStart: ZonedDateTime,
        internalDeadline: ZonedDateTime,
        externalDeadline: ZonedDateTime,
        budget: BigDecimal,
        currencyCode: CurrencyCode,
        clientId: ClientId
    ): Project

    fun update(
        id: ProjectId,
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguages: List<LanguageCode>,
        accuracyId: AccuracyId,
        industryId: IndustryId,
        unitId: UnitId,
        amount: Int,
        budget: BigDecimal,
        currencyCode: Currency,
        clientId: ClientId
    ): Project

    fun moveStart(id: ProjectId, expectedStart: ZonedDateTime): Project
    fun moveDeadlines(id: ProjectId, internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime): Project
    fun addTeamMember(id: ProjectId, teamMember: TeamMember): Project
    fun removeTeamMember(id: ProjectId, teamMember: TeamMember): Project
    fun addTask(id: ProjectId, task: Task): Project
    fun removeTask(id: ProjectId, task: Task): Project
    fun addExpense(id: ProjectId, expense: Expense): Project
    fun removeExpense(id: ProjectId, expense: Expense): Project
    fun addNote(id: ProjectId, note: Note): Project
    fun addFile(id: ProjectId, file: File): Project
    fun addChat(id: ProjectId, chat: Chat): Project
    fun finishDraft(id: ProjectId): Project
    fun backToDraft(id: ProjectId): Project
    fun startProgress(id: ProjectId): Project
    fun finishProgress(id: ProjectId): Project
    fun backToProgress(id: ProjectId): Project
    fun deliver(id: ProjectId): Project
    fun invoice(id: ProjectId): Project
    fun pay(id: ProjectId): Project
    fun putOnHold(id: ProjectId): Project
    fun resume(id: ProjectId): Project
    fun cancel(id: ProjectId): Project
    fun reopen(id: ProjectId): Project
}
