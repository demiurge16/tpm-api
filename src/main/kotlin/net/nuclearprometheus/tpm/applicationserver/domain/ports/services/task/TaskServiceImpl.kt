package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import java.math.BigDecimal
import java.time.ZonedDateTime

class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val serviceTypeRepository: ServiceTypeRepository,
    private val currencyRepository: CurrencyRepository,
    private val priorityRepository: PriorityRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val logger: Logger
) : TaskService {

    override fun create(
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguage: LanguageCode,
        accuracy: AccuracyId,
        industry: IndustryId,
        unit: UnitId,
        serviceTypeId: ServiceTypeId,
        amount: Int,
        expectedStart: ZonedDateTime,
        deadline: ZonedDateTime,
        budget: BigDecimal,
        currency: CurrencyCode,
        priorityId: PriorityId,
        projectId: ProjectId
    ): Task {
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project not found")

        val task = Task(
            title = title,
            description = description,
            sourceLanguage = languageRepository.get(sourceLanguage) ?: throw NotFoundException("Source language not found"),
            targetLanguage = languageRepository.get(targetLanguage) ?: throw NotFoundException("Target language not found"),
            accuracy = accuracyRepository.get(accuracy) ?: throw NotFoundException("Accuracy not found"),
            industry = industryRepository.get(industry) ?: throw NotFoundException("Industry not found"),
            unit = unitRepository.get(unit) ?: throw NotFoundException("Unit not found"),
            serviceType = serviceTypeRepository.get(serviceTypeId) ?: throw NotFoundException("Service type not found"),
            amount = amount,
            expectedStart = expectedStart,
            deadline = deadline,
            budget = budget,
            currency = currencyRepository.get(currency) ?: throw NotFoundException("Currency not found"),
            priority = priorityRepository.get(priorityId) ?: throw NotFoundException("Priority not found"),
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
        serviceTypeId: ServiceTypeId,
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
            serviceType = serviceTypeRepository.get(serviceTypeId) ?: throw NotFoundException("Service type not found"),
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

    override fun changePriority(id: TaskId, priorityId: PriorityId) = taskRepository.get(id)?.let { task ->
        task.changePriority(priorityRepository.get(priorityId) ?: throw NotFoundException("Priority not found"))
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun assign(id: TaskId, assigneeId: UserId) = taskRepository.get(id)?.let { task ->
        val user = userRepository.get(assigneeId) ?: throw NotFoundException("User with id $assigneeId not found")
        teamMemberRepository.getByUserIdAndProjectId(user.id, task.projectId)
            ?: throw NotFoundException("User with id $assigneeId is not a member of project with id ${task.projectId}")

        task.assign(user)
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun unassign(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.unassign()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun start(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.start()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun startReview(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.startReview()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun reject(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.reject()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun approve(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.approve()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun putOnHold(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.putOnHold()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun resume(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.resume()
        taskRepository.update(task)
    } ?: throw NotFoundException("Task not found")

    override fun complete(id: TaskId) = taskRepository.get(id)?.let { task ->
        task.complete()
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