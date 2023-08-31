package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface TaskRepository : BaseRepository<Task, TaskId> {
    fun getAllByProjectId(projectId: ProjectId): List<Task>
    fun getAllByProjectIdAndQuery(projectId: ProjectId, query: Query<Task>): Page<Task>
    fun deleteAllByProjectId(projectId: ProjectId)
}
