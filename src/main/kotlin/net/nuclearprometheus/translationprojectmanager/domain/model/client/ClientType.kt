package net.nuclearprometheus.translationprojectmanager.domain.model.client

class ClientType(id: ClientTypeId = ClientTypeId(), name: String, description: String, corporate: Boolean, active: Boolean = true) {
    var id: ClientTypeId = id; private set
    var name: String = name; private set
    var description: String = description; private set
    var corporate: Boolean = corporate; private set
    var active: Boolean = active; private set

    fun update(name: String, description: String, corporate: Boolean) {
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
