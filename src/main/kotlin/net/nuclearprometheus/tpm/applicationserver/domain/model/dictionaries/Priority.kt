package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate

class Priority(
    id: PriorityId = PriorityId(),
    name: String,
    description: String,
    emoji: String,
    value: Int,
    active: Boolean = true
) : Entity<PriorityId>(id) {

    init {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { emoji.isNotBlank() } otherwise {
                ValidationError("emoji", "Emoji cannot be blank")
            }
            assert { value >= 0 } otherwise {
                ValidationError("value", "Value must be greater than or equal to 0")
            }
        }
    }

    var name = name; private set
    var description = description; private set
    var emoji = emoji; private set
    var value = value; private set
    var active = active; private set

    fun update(name: String, description: String, emoji: String, value: Int) {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { emoji.isNotBlank() } otherwise {
                ValidationError("emoji", "Emoji cannot be blank")
            }
            assert { value >= 0 } otherwise {
                ValidationError("value", "Value must be greater than or equal to 0")
            }
        }

        this.name = name
        this.description = description
        this.emoji = emoji
        this.value = value
    }

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}
