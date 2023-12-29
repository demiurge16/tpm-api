package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ExpenseCategoryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Component
class ExpenseSpecificationFactory : SpecificationFactory<Expense, ExpenseDatabaseModel>() {

    override val filterPredicates = filterPredicates<Expense, ExpenseDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        comparable("amount") { root, _, _ -> root.get<BigDecimal>("amount") }
        uniqueValue("currency") { root, _, _ -> root.get<String>("currency") }
        comparable("date") { root, _, _ -> root.get<ZonedDateTime>("date") }
        uniqueValue("categoryId") { root, _, _ ->
            val category = root.join<ExpenseDatabaseModel, ExpenseCategoryDatabaseModel>("category")
            category.get<UUID>("id")
        }
        uniqueValue("spenderId") { root, _, _ -> root.get<UUID>("spenderId") }
        uniqueValue("projectId") { root, _, _ -> root.get<UUID>("projectId") }
    }
}