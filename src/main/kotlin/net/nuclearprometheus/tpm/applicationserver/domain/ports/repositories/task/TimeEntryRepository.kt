package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TimeEntryRepository : BaseRepository<TimeEntry, TimeEntryId>
