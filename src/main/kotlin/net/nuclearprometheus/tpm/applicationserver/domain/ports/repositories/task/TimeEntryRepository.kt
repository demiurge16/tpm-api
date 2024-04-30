package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface TimeEntryRepository : BaseRepository<TimeEntry, TimeEntryId> {
    fun getForTask(taskId: TaskId, query: Query<TimeEntry>): Page<TimeEntry>
}
