package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId

interface ExpenseCategoryService {
    fun create(name: String, description: String): ExpenseCategory
    fun update(id: ExpenseCategoryId, name: String, description: String): ExpenseCategory
    fun activate(id: ExpenseCategoryId): ExpenseCategory
    fun deactivate(id: ExpenseCategoryId): ExpenseCategory
}