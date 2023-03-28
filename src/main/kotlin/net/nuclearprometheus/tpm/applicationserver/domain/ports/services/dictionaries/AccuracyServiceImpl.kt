package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.AccuracyRepository

class AccuracyServiceImpl(
    private val accuracyRepository: AccuracyRepository
) : AccuracyService {
    override fun getAll(): List<Accuracy> {
        return accuracyRepository.getAll()
    }

    override fun get(id: AccuracyId): Accuracy? {
        return accuracyRepository.get(id)
    }

    override fun create(name: String, description: String): Accuracy {
        val accuracy = Accuracy(name = name, description = description)

        return accuracyRepository.create(accuracy)
    }

    override fun update(id: AccuracyId, name: String, description: String): Accuracy {
        val accuracy = accuracyRepository.get(id) ?: throw IllegalArgumentException("Accuracy with id $id does not exist")
        accuracy.update(name, description)

        return accuracyRepository.update(accuracy)
    }

    override fun activate(id: AccuracyId): Accuracy {
        val accuracy = accuracyRepository.get(id) ?: throw IllegalArgumentException("Accuracy with id $id does not exist")
        accuracy.activate()

        return accuracyRepository.update(accuracy)
    }

    override fun deactivate(id: AccuracyId): Accuracy {
        val accuracy = accuracyRepository.get(id) ?: throw IllegalArgumentException("Accuracy with id $id does not exist")
        accuracy.deactivate()

        return accuracyRepository.update(accuracy)
    }
}