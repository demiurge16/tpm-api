package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

class Industry(
    val id: IndustryId = IndustryId(),
    name: String,
    description: String,
    active: Boolean = true
) {
    var name = name; private set
    var description = description; private set
    var active = active; private set

    fun update(name: String, description: String) {
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
