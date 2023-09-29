package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate

class ServiceType(
    id: ServiceTypeId = ServiceTypeId(),
    name: String,
    description: String,
    active: Boolean = true
) : Entity<ServiceTypeId>(id) {

    var name = name; private set
    var description = description; private set
    var active = active; private set

    fun update(name: String, description: String) {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
        }

        this.name = name
        this.description = description
    }

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}