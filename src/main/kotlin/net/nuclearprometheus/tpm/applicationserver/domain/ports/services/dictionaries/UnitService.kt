package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit

interface UnitService {
    fun create(name: String, description: String): Unit
    fun update(id: UnitId, name: String, description: String): Unit
    fun activate(id: UnitId): Unit
    fun deactivate(id: UnitId): Unit
}
