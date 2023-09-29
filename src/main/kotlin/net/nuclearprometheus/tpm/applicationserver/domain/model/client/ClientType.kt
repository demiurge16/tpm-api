package net.nuclearprometheus.tpm.applicationserver.domain.model.client

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate

class ClientType(
    id: ClientTypeId = ClientTypeId(),
    name: String,
    description: String,
    corporate: Boolean,
    active: Boolean = true
): Entity<ClientTypeId>(id) {

    init {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
        }
    }

    var name: String = name; private set
    var description: String = description; private set
    var corporate: Boolean = corporate; private set
    var active: Boolean = active; private set

    fun update(name: String, description: String, corporate: Boolean) {
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
        this.corporate = corporate
    }

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}
