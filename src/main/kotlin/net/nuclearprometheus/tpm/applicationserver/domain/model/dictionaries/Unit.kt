package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

class Unit(
    val id: UnitId = UnitId(),
    name: String,
    description: String,
    active: Boolean = true
) {
    var name: String = name
        private set
    var description: String = description
        private set
    var active: Boolean = active
        private set

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
