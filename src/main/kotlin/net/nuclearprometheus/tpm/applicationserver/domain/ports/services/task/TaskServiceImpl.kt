package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import java.math.BigDecimal
import java.time.ZonedDateTime

class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository
) : TaskService {

    override fun create(
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguage: LanguageCode,
        accuracy: AccuracyId,
        industry: IndustryId,
        unit: UnitId,
        amount: Int,
        expectedStart: ZonedDateTime,
        deadline: ZonedDateTime,
        budget: BigDecimal,
        currency: CurrencyCode,
        projectId: ProjectId
    ): Task {
        projectRepository.get(projectId) ?: throw NotFoundException("Project not found")

        val task = Task(
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage) ?: throw NotFoundException("Source language not found"),
            targetLanguage = languageRepository.get(targetLanguage) ?: throw NotFoundException("Target language not found"),
            accuracy = accuracyRepository.get(accuracy) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industry) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unit) ?: throw NotFoundException("Unit not found"),
            amount = amount,
            expectedStart = expectedStart,
            deadline = deadline,
            budget = budget,
            currency = currencyRepository.get(currency) ?: throw NotFoundException("Currency not found"),
            projectId = projectId
        )

        return taskRepository.create(task)
    }

    override fun update(
        id: TaskId,
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguage: LanguageCode,
        accuracy: AccuracyId,
        industry: IndustryId,
        unit: UnitId,
        amount: Int,
        budget: BigDecimal,
        currency: CurrencyCode
    ): Task {
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")

        task.update(
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage) ?: throw NotFoundException("Source language not found"),
            targetLanguage = languageRepository.get(targetLanguage) ?: throw NotFoundException("Target language not found"),
            accuracy = accuracyRepository.get(accuracy) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industry) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unit) ?: throw NotFoundException("Unit not found"),
            amount = amount,
            budget = budget,
            currency = currencyRepository.get(currency) ?: throw NotFoundException("Currency not found")
        )

        return taskRepository.update(task)
    }

    override fun moveStart(id: TaskId, expectedStart: ZonedDateTime) = taskRepository.get(id)?.let { task ->
        task.moveStart(expectedStart)
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun moveDeadline(id: TaskId, deadline: ZonedDateTime) = taskRepository.get(id)?.let { task ->
        task.moveDeadline(deadline)
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun assignTeamMember(id: TaskId, teamMemberId: TeamMemberId) = taskRepository.get(id)?.let { task ->
        val teamMember = teamMemberRepository.get(teamMemberId) ?: throw NotFoundException("Team member not found")

        task.assignTeamMember(teamMember)
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun unassignTeamMember(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.unassignTeamMember()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun start(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.start()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun complete(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.complete()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun requestRevisions(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.requestRevisions()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun completeRevisions(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.completeRevisions()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun deliver(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.deliver()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun cancel(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.cancel()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun reopen(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.reopen()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")
}