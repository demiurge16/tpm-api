package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.PriorityId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface PriorityRepository : BaseRepository<Priority, PriorityId>
