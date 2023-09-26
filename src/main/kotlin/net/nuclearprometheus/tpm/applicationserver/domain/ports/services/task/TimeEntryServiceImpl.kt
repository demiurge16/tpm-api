package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TimeEntryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import java.time.LocalDate
import java.time.ZonedDateTime

class TimeEntryServiceImpl(
    private val timeEntryRepository: TimeEntryRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository,
    private val logger: Logger
) : TimeEntryService {

    override fun create(
        userId: UserId,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String,
        taskId: TaskId
    ): TimeEntry {
        logger.info("Creating time entry for user $userId, date $date, time spent $timeSpent, time unit $timeUnit, description $description, task $taskId")

        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")
        val user = userRepository.get(userId) ?: throw NotFoundException("User not found")

        validateUserMembership(user, project)
        validateCreatorPermissions(currentUser, user, project)

        val timeEntry = TimeEntry(
            user = user,
            date = date,
            timeSpent = timeSpent,
            timeUnit = timeUnit,
            description = description,
            taskId = taskId
        )

        return timeEntryRepository.create(timeEntry)
    }

    override fun createSubmitted(
        userId: UserId,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String,
        taskId: TaskId
    ): TimeEntry {
        logger.info("Creating submitted time entry for user $userId, date $date, time spent $timeSpent, time unit $timeUnit, description $description, task $taskId")

        val currentUser = userContextProvider.getCurrentUser()
        val task = taskRepository.get(taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")
        val user = userRepository.get(userId) ?: throw NotFoundException("User not found")

        validateUserMembership(user, project)
        validateCreatorPermissions(currentUser, user, project)

        val timeEntry = TimeEntry(
            user = user,
            date = date,
            timeSpent = timeSpent,
            timeUnit = timeUnit,
            status = TimeEntryStatus.SUBMITTED,
            description = description,
            taskId = taskId
        )

        return timeEntryRepository.create(timeEntry)
    }

    private fun validateUserMembership(user: User, project: Project) {
        if (!project.hasTeamMember(user.id)) {
            logger.error("User ${user.id} is not a member of the project ${project.id}")
            throw ProjectAccessException("User is not a member of the project")
        }
    }

    private fun validateCreatorPermissions(currentUser: User, user: User, project: Project) {
        val isCurrentUserAdmin = currentUser.hasRole(UserRole.ADMIN)
        val isCurrentUserProjectManager = project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)

        when {
            isCurrentUserAdmin || isCurrentUserProjectManager -> return
            currentUser.id == user.id -> return
            else -> {
                logger.error("User ${currentUser.id} has insufficient permissions to create time entry for user ${user.id}")
                throw ProjectAccessException("Only admins and project managers can create time entries for other users")
            }
        }
    }

    override fun update(
        id: TimeEntryId,
        userId: UserId,
        date: LocalDate,
        timeSpent: Int,
        timeUnit: TimeUnit,
        description: String
    ): TimeEntry {
        logger.info("Updating time entry $id for user $userId, date $date, time spent $timeSpent, time unit $timeUnit, description $description")

        val currentUser = userContextProvider.getCurrentUser()
        val timeEntry = timeEntryRepository.get(id) ?: throw NotFoundException("Time entry not found")
        val task = taskRepository.get(timeEntry.taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")
        val user = userRepository.get(userId) ?: throw NotFoundException("User not found")

        validateUserMembershipForUpdate(user, project)
        validateEditorPermissions(currentUser, user, project)

        timeEntry.update(
            user = user,
            date = date,
            timeSpent = timeSpent,
            timeUnit = timeUnit,
            description = description
        )

        return timeEntryRepository.update(timeEntry)
    }

    private fun validateUserMembershipForUpdate(user: User, project: Project) {
        if (!project.hasTeamMember(user.id)) {
            logger.error("User ${user.id} is not a member of the project ${project.id}")
            throw ProjectAccessException("Time entries can only be edited for tasks in projects where the user is a team member")
        }
    }

    private fun validateEditorPermissions(currentUser: User, user: User, project: Project) {
        val isCurrentUserAdmin = currentUser.hasRole(UserRole.ADMIN)
        val isCurrentUserProjectManager = project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)

        when {
            isCurrentUserAdmin || isCurrentUserProjectManager -> return
            currentUser.id == user.id -> return
            else -> {
                logger.error("User ${currentUser.id} has insufficient permissions to edit time entry for user ${user.id}")
                throw ProjectAccessException("Only admins and project managers can edit time entries for other users")
            }
        }
    }

    override fun delete(id: TimeEntryId) {
        logger.info("Deleting time entry $id")

        val currentUser = userContextProvider.getCurrentUser()
        val timeEntry = timeEntryRepository.get(id) ?: throw NotFoundException("Time entry not found")
        val task = taskRepository.get(timeEntry.taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        validateDeletionPermissions(currentUser, timeEntry, project)

        timeEntryRepository.delete(id)
    }

    private fun validateDeletionPermissions(currentUser: User, timeEntry: TimeEntry, project: Project) {
        val isCurrentUserAdmin = currentUser.hasRole(UserRole.ADMIN)
        val isCurrentUserProjectManager = project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)

        if (!(isCurrentUserAdmin || isCurrentUserProjectManager || currentUser.id == timeEntry.user.id)) {
            logger.error("User ${currentUser.id} has insufficient permissions to delete time entry ${timeEntry.id}")
            throw ProjectAccessException("Only admins and project managers can delete time entries")
        }
    }

    override fun submit(id: TimeEntryId): TimeEntry {
        logger.info("Submitting time entry $id")

        val currentUser = userContextProvider.getCurrentUser()
        val timeEntry = timeEntryRepository.get(id) ?: throw NotFoundException("Time entry not found")
        val task = taskRepository.get(timeEntry.taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        validateSubmitPermissions(currentUser, timeEntry, project)

        timeEntry.submit()
        return timeEntryRepository.update(timeEntry)
    }

    private fun validateSubmitPermissions(currentUser: User, timeEntry: TimeEntry, project: Project) {
        val isCurrentUserAdmin = currentUser.hasRole(UserRole.ADMIN)
        val isCurrentUserProjectManager = project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)

        if (!(isCurrentUserAdmin || isCurrentUserProjectManager || currentUser.id == timeEntry.user.id)) {
            logger.error("User ${currentUser.id} has insufficient permissions to submit time entry ${timeEntry.id}")
            throw ProjectAccessException("Only admins and project managers can submit time entries")
        }
    }

    override fun approve(id: TimeEntryId): TimeEntry {
        logger.info("Approving time entry $id")

        val currentUser = userContextProvider.getCurrentUser()
        val timeEntry = timeEntryRepository.get(id) ?: throw NotFoundException("Time entry not found")
        val task = taskRepository.get(timeEntry.taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        validateApprovePermissions(currentUser, timeEntry, project)

        timeEntry.approve()
        return timeEntryRepository.update(timeEntry)
    }

    private fun validateApprovePermissions(currentUser: User, timeEntry: TimeEntry, project: Project) {
        val isCurrentUserAdmin = currentUser.hasRole(UserRole.ADMIN)
        val isCurrentUserProjectManager = project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)

        if (!(isCurrentUserAdmin || isCurrentUserProjectManager)) {
            logger.error("User ${currentUser.id} has insufficient permissions to approve time entry ${timeEntry.id}")
            throw ProjectAccessException("Only admins and project managers can approve time entries")
        }
    }

    override fun reject(id: TimeEntryId): TimeEntry {
        logger.info("Rejecting time entry $id")

        val currentUser = userContextProvider.getCurrentUser()
        val timeEntry = timeEntryRepository.get(id) ?: throw NotFoundException("Time entry not found")
        val task = taskRepository.get(timeEntry.taskId) ?: throw NotFoundException("Task not found")
        val project = projectRepository.get(task.projectId) ?: throw IllegalStateException("Project not found")

        validateRejectPermissions(currentUser, timeEntry, project)

        timeEntry.reject()
        return timeEntryRepository.update(timeEntry)
    }

    private fun validateRejectPermissions(currentUser: User, timeEntry: TimeEntry, project: Project) {
        val isCurrentUserAdmin = currentUser.hasRole(UserRole.ADMIN)
        val isCurrentUserProjectManager = project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)

        if (!(isCurrentUserAdmin || isCurrentUserProjectManager)) {
            logger.error("User ${currentUser.id} has insufficient permissions to reject time entry ${timeEntry.id}")
            throw ProjectAccessException("Only admins and project managers can reject time entries")
        }
    }
}
