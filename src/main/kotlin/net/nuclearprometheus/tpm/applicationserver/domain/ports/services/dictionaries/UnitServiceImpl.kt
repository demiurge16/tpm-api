package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId

class UnitServiceImpl : UnitService {
    override fun getAll(): List<Unit> {
        TODO("Not yet implemented")
    }

    override fun get(id: UnitId): Unit? {
        TODO("Not yet implemented")
    }

    override fun create(name: String, description: String): Unit {
        TODO("Not yet implemented")
    }

    override fun update(id: UnitId, name: String, description: String): Unit {
        TODO("Not yet implemented")
    }

    override fun activate(id: UnitId) {
        TODO("Not yet implemented")
    }

    override fun deactivate(id: UnitId) {
        TODO("Not yet implemented")
    }
}