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

class ProjectServiceImpl : ProjectService {
    override fun getAll(): List<Project> {
        TODO("Not yet implemented")
    }

    override fun get(id: ProjectId): Project? {
        TODO("Not yet implemented")
    }

    override fun create(
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
    ): Project {
        TODO("Not yet implemented")
    }

    override fun update(
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
    ): Project {
        TODO("Not yet implemented")
    }

    override fun moveStart(id: ProjectId, expectedStart: ZonedDateTime): Project {
        TODO("Not yet implemented")
    }

    override fun moveDeadlines(
        id: ProjectId,
        internalDeadline: ZonedDateTime,
        externalDeadline: ZonedDateTime
    ): Project {
        TODO("Not yet implemented")
    }

    override fun addTeamMember(id: ProjectId, teamMember: TeamMember): Project {
        TODO("Not yet implemented")
    }

    override fun removeTeamMember(id: ProjectId, teamMember: TeamMember): Project {
        TODO("Not yet implemented")
    }

    override fun addTask(id: ProjectId, task: Task): Project {
        TODO("Not yet implemented")
    }

    override fun removeTask(id: ProjectId, task: Task): Project {
        TODO("Not yet implemented")
    }

    override fun addExpense(id: ProjectId, expense: Expense): Project {
        TODO("Not yet implemented")
    }

    override fun removeExpense(id: ProjectId, expense: Expense): Project {
        TODO("Not yet implemented")
    }

    override fun addNote(id: ProjectId, note: Note): Project {
        TODO("Not yet implemented")
    }

    override fun addFile(id: ProjectId, file: File): Project {
        TODO("Not yet implemented")
    }

    override fun addChat(id: ProjectId, chat: Chat): Project {
        TODO("Not yet implemented")
    }

    override fun finishDraft(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun backToDraft(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun startProgress(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun finishProgress(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun backToProgress(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun deliver(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun invoice(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun pay(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun putOnHold(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun resume(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun cancel(id: ProjectId): Project {
        TODO("Not yet implemented")
    }

    override fun reopen(id: ProjectId): Project {
        TODO("Not yet implemented")
    }
}