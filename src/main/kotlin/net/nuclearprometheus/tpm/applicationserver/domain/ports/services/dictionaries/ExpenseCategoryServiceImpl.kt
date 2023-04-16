package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class ExpenseCategoryServiceImpl(
    private val expenseCategoryRepository: ExpenseCategoryRepository,
    private val logger: Logger
) : ExpenseCategoryService {

    override fun create(name: String, description: String): ExpenseCategory {
        val expenseCategory = ExpenseCategory(name = name, description = description)

        return expenseCategoryRepository.create(expenseCategory)
    }

    override fun update(id: ExpenseCategoryId, name: String, description: String): ExpenseCategory {
        val expenseCategory = expenseCategoryRepository.get(id) ?: throw NotFoundException("ExpenseCategory with id $id does not exist")
        expenseCategory.update(name, description)

        return expenseCategoryRepository.update(expenseCategory)
    }

    override fun activate(id: ExpenseCategoryId): ExpenseCategory {
        val expenseCategory = expenseCategoryRepository.get(id) ?: throw NotFoundException("ExpenseCategory with id $id does not exist")
        expenseCategory.activate()

        return expenseCategoryRepository.update(expenseCategory)
    }

    override fun deactivate(id: ExpenseCategoryId): ExpenseCategory {
        val expenseCategory = expenseCategoryRepository.get(id) ?: throw NotFoundException("ExpenseCategory with id $id does not exist")
        expenseCategory.deactivate()

        return expenseCategoryRepository.update(expenseCategory)
    }
}