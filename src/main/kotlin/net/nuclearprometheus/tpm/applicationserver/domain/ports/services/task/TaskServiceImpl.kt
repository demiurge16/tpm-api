package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.task.TaskAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
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
    private val userContextProvider: UserContextProvider,
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
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project not found")

        if (!project.hasTeamMember(currentUser.id)) {
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

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
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not allowed to update task ${task.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to update task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to update task ${task.id}")
        }

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

    override fun moveStart(id: TaskId, expectedStart: ZonedDateTime): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            logger.error("User ${currentUser.id} is not allowed to update task ${task.id} start")
            throw TaskAccessException("User ${currentUser.id} is not allowed to update task ${task.id} start")
        }

        task.moveStart(expectedStart)
        return taskRepository.update(task)
    }

    override fun moveDeadline(id: TaskId, deadline: ZonedDateTime): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            logger.error("User ${currentUser.id} is not allowed to update task ${task.id} deadline")
            throw TaskAccessException("User ${currentUser.id} is not allowed to update task ${task.id} deadline")
        }

        task.moveDeadline(deadline)
        return taskRepository.update(task)
    }

    override fun changePriority(id: TaskId, priorityId: PriorityId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            logger.error("User ${currentUser.id} is not allowed to update task ${task.id} priority")
            throw TaskAccessException("User ${currentUser.id} is not allowed to update task ${task.id} priority")
        }

        task.changePriority(priorityRepository.get(priorityId) ?: throw NotFoundException("Priority not found"))
        taskRepository.update(task)
        return task
    }

    override fun assign(id: TaskId, assigneeId: UserId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!project.hasTeamMember(assigneeId)) {
            logger.error("User ${assigneeId} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${assigneeId} is not a member of project ${project.id}")
        }

        val assignee = userRepository.get(assigneeId) ?: throw NotFoundException("Assignee not found")
        task.assign(assignee)
        taskRepository.update(task)
        return task
    }

    override fun unassign(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        task.unassign()
        taskRepository.update(task)
        return task
    }

    override fun start(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to start task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to start task ${task.id}")
        }

        task.start()
        taskRepository.update(task)
        return task
    }

    override fun startReview(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to start review of task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to start review of task ${task.id}")
        }

        task.startReview()
        taskRepository.update(task)
        return task
    }

    override fun reject(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to reject task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to reject task ${task.id}")
        }

        task.reject()
        taskRepository.update(task)
        return task
    }

    override fun approve(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to approve task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to approve task ${task.id}")
        }

        task.approve()
        taskRepository.update(task)
        return task
    }

    override fun putOnHold(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to put task ${task.id} on hold")
            throw TaskAccessException("User ${currentUser.id} is not allowed to put task ${task.id} on hold")
        }

        task.putOnHold()
        taskRepository.update(task)
        return task
    }

    override fun resume(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to resume task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to resume task ${task.id}")
        }

        task.resume()
        taskRepository.update(task)
        return task
    }

    override fun complete(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${project.id}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${project.id}")
        }

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER) || task.assignee?.id != currentUser.id) {
            logger.error("User ${currentUser.id} is not allowed to complete task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to complete task ${task.id}")
        }

        task.complete()
        taskRepository.update(task)
        return task
    }

    override fun cancel(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            logger.error("User ${currentUser.id} is not allowed to complete task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to complete task ${task.id}")
        }

        task.cancel()
        taskRepository.update(task)
        return task
    }

    override fun reopen(id: TaskId): Task {
        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(id) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        if (!currentUser.hasRole(UserRole.ADMIN) || !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            logger.error("User ${currentUser.id} is not allowed to reopen task ${task.id}")
            throw TaskAccessException("User ${currentUser.id} is not allowed to reopen task ${task.id}")
        }

        task.reopen()
        taskRepository.update(task)
        return task
    }
}