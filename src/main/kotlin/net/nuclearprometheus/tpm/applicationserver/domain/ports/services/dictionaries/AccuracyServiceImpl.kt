package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId

class AccuracyServiceImpl : AccuracyService {
    override fun getAll(): List<Accuracy> {
        TODO("Not yet implemented")
    }

    override fun get(id: AccuracyId): Accuracy? {
        TODO("Not yet implemented")
    }

    override fun create(name: String, description: String): Accuracy {
        TODO("Not yet implemented")
    }

    override fun update(id: AccuracyId, name: String, description: String): Accuracy {
        TODO("Not yet implemented")
    }

    override fun activate(id: AccuracyId) {
        TODO("Not yet implemented")
    }

    override fun deactivate(id: AccuracyId) {
        TODO("Not yet implemented")
    }
}