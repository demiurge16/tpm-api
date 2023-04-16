package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import java.math.BigDecimal
import java.time.ZonedDateTime

class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val clientRepository: ClientRepository,
    private val logger: Logger
) : ProjectService {

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
        val project = Project(
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage)
                ?: throw NotFoundException("Source language not found"),
            targetLanguages = languageRepository.get(targetLanguages),
            accuracy = accuracyRepository.get(accuracyId) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industryId) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unitId) ?: throw NotFoundException("Unit not found"),
            amount = amount,
            expectedStart = expectedStart,
            internalDeadline = internalDeadline,
            externalDeadline = externalDeadline,
            budget = budget,
            currency = currencyRepository.get(currencyCode) ?: throw NotFoundException("Currency not found"),
            client = clientRepository.get(clientId) ?: throw NotFoundException("Client not found")
        )

        return projectRepository.create(project)
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
        currencyCode: CurrencyCode,
        clientId: ClientId
    ): Project {
        val project = projectRepository.get(id) ?: throw NotFoundException("Project not found")

        project.update(
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage)
                ?: throw NotFoundException("Source language not found"),
            targetLanguages = languageRepository.get(targetLanguages),
            accuracy = accuracyRepository.get(accuracyId) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industryId) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unitId) ?: throw NotFoundException("Unit not found"),
            amount = amount,
            budget = budget,
            currency = currencyRepository.get(currencyCode) ?: throw NotFoundException("Currency not found"),
            client = clientRepository.get(clientId) ?: throw NotFoundException("Client not found")
        )

        return projectRepository.update(project)
    }

    override fun moveStart(id: ProjectId, expectedStart: ZonedDateTime) = projectRepository.get(id)?.let { project ->
        project.moveStart(expectedStart)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun moveDeadlines(id: ProjectId, internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime) =
        projectRepository.get(id)?.let { project ->
            project.moveDeadlines(internalDeadline, externalDeadline)
            projectRepository.update(project)
        } ?: throw NotFoundException("Project not found")

    override fun addTeamMember(id: ProjectId, teamMember: TeamMember) = projectRepository.get(id)?.let { project ->
        project.addTeamMember(teamMember)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun removeTeamMember(id: ProjectId, teamMember: TeamMember) = projectRepository.get(id)?.let { project ->
        project.removeTeamMember(teamMember)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun addTask(id: ProjectId, task: Task) = projectRepository.get(id)?.let { project ->
        project.addTask(task)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun removeTask(id: ProjectId, task: Task) = projectRepository.get(id)?.let { project ->
        project.removeTask(task)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun addExpense(id: ProjectId, expense: Expense) = projectRepository.get(id)?.let { project ->
        project.addExpense(expense)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun removeExpense(id: ProjectId, expense: Expense) = projectRepository.get(id)?.let { project ->
        project.removeExpense(expense)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun addNote(id: ProjectId, note: Note) = projectRepository.get(id)?.let { project ->
        project.addNote(note)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun addFile(id: ProjectId, file: File) = projectRepository.get(id)?.let { project ->
        project.addFile(file)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun addChat(id: ProjectId, chat: Chat) = projectRepository.get(id)?.let { project ->
        project.addChat(chat)
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun finishDraft(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.finishDraft()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun backToDraft(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.backToDraft()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun startProgress(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.startProgress()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun finishProgress(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.finishProgress()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun backToProgress(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.backToProgress()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun deliver(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.deliver()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun invoice(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.invoice()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun pay(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.pay()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun putOnHold(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.putOnHold()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun resume(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.resume()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun cancel(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.cancel()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")

    override fun reopen(id: ProjectId) = projectRepository.get(id)?.let { project ->
        project.reopen()
        projectRepository.update(project)
    } ?: throw NotFoundException("Project not found")
}
