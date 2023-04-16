package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

class ExpenseCategory(
    id: ExpenseCategoryId = ExpenseCategoryId(),
    name: String,
    description: String,
    active: Boolean = true
) : Entity<ExpenseCategoryId>(id) {

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
