package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TaskRepository : BaseRepository<Task, TaskId> {
    fun getAllByProjectId(projectId: ProjectId): List<Task>
    fun deleteAllByProjectId(projectId: ProjectId)
}
