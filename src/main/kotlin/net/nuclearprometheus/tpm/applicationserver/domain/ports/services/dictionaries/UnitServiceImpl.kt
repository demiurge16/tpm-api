package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnitId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.UnitRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class UnitServiceImpl(
    private val unitRepository: UnitRepository,
    private val logger: Logger
) : UnitService {

    override fun create(name: String, description: String, volume: Int, measurement: Measurement): Unit {
        val unit = Unit(name = name, description = description, volume = volume, measurement = measurement)

        return unitRepository.create(unit)
    }

    override fun update(id: UnitId, name: String, description: String, volume: Int, measurement: Measurement): Unit {
        val unit = unitRepository.get(id) ?: throw NotFoundException("Unit with id $id does not exist")
        unit.update(name, description, volume, measurement)

        return unitRepository.update(unit)
    }

    override fun activate(id: UnitId): Unit {
        val unit = unitRepository.get(id) ?: throw NotFoundException("Unit with id $id does not exist")
        unit.activate()

        return unitRepository.update(unit)
    }

    override fun deactivate(id: UnitId): Unit {
        val unit = unitRepository.get(id) ?: throw NotFoundException("Unit with id $id does not exist")
        unit.deactivate()

        return unitRepository.update(unit)
    }
}