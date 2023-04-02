package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.PriorityId

interface PriorityService {
    fun create(name: String, description: String, emoji: String, value: Int): Priority
    fun update(id: PriorityId, name: String, description: String, emoji: String, value: Int): Priority
    fun activate(id: PriorityId): Priority
    fun deactivate(id: PriorityId): Priority
}
