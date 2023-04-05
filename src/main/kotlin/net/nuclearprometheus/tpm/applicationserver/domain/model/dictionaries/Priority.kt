package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

class Priority(
    id: PriorityId = PriorityId(),
    name: String,
    description: String,
    emoji: String,
    value: Int,
    active: Boolean = true
) : Entity<PriorityId>(id) {

    var name = name; private set
    var description = description; private set
    var emoji = emoji; private set
    var value = value; private set
    var active = active; private set

    fun update(name: String, description: String, emoji: String, value: Int) {
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
