package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.PriorityId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.PriorityRepository

class PriorityServiceImpl(
    private val priorityRepository: PriorityRepository
) : PriorityService {
    override fun create(name: String, description: String, emoji: String, value: Int): Priority {
        val priority = Priority(name = name, description = description, emoji = emoji, value = value)

        return priorityRepository.create(priority)
    }

    override fun update(id: PriorityId, name: String, description: String, emoji: String, value: Int): Priority {
        val priority = priorityRepository.get(id) ?: throw IllegalArgumentException("Priority with id $id does not exist")
        priority.update(name, description, emoji, value)

        return priorityRepository.update(priority)
    }

    override fun activate(id: PriorityId): Priority {
        val priority = priorityRepository.get(id) ?: throw IllegalArgumentException("Priority with id $id does not exist")
        priority.activate()

        return priorityRepository.update(priority)
    }

    override fun deactivate(id: PriorityId): Priority {
        val priority = priorityRepository.get(id) ?: throw IllegalArgumentException("Priority with id $id does not exist")
        priority.deactivate()

        return priorityRepository.update(priority)
    }
}