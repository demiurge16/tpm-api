package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate

class Unit(
    id: UnitId = UnitId(),
    name: String,
    description: String,
    volume: Int,
    measurement: Measurement,
    active: Boolean = true
) : Entity<UnitId>(id) {

    init {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { volume > 0 } otherwise {
                ValidationError("volume", "Volume must be greater than 0")
            }
        }
    }

    var name = name; private set
    var description = description; private set
    var volume = volume; private set
    var measurement = measurement; private set
    var active = active; private set

    fun update(name: String, description: String, volume: Int, measurement: Measurement) {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { volume > 0 } otherwise {
                ValidationError("volume", "Volume must be greater than 0")
            }
        }

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
