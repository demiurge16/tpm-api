package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId

class IndustryServiceImpl : IndustryService {
    override fun getAll(): List<Industry> {
        TODO("Not yet implemented")
    }

    override fun get(id: IndustryId): Industry? {
        TODO("Not yet implemented")
    }

    override fun create(name: String, description: String): Industry {
        TODO("Not yet implemented")
    }

    override fun update(id: IndustryId, name: String, description: String): Industry {
        TODO("Not yet implemented")
    }

    override fun activate(id: IndustryId) {
        TODO("Not yet implemented")
    }

    override fun deactivate(id: IndustryId) {
        TODO("Not yet implemented")
    }
}