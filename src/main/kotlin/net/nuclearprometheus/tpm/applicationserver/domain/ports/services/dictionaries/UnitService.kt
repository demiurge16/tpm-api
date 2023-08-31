package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId

interface UnitService {
    fun create(name: String, description: String, volume: Int, measurement: Measurement): Unit
    fun update(id: UnitId, name: String, description: String, volume: Int, measurement: Measurement): Unit
    fun activate(id: UnitId): Unit
    fun deactivate(id: UnitId): Unit
}
