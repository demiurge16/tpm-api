package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

class Unit(
    id: UnitId = UnitId(),
    name: String,
    description: String,
    volume: Int,
    measurement: Measurement,
    active: Boolean = true
) : Entity<UnitId>(id) {
    var name = name; private set
    var description = description; private set
    var volume = volume; private set
    var measurement = measurement; private set
    var active = active; private set

    fun update(name: String, description: String, volume: Int, measurement: Measurement) {
        this.name = name
        this.description = description
        this.volume = volume
        this.measurement = measurement
    }

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}
