package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TaskRepository : BaseRepository<Task, TaskId>
