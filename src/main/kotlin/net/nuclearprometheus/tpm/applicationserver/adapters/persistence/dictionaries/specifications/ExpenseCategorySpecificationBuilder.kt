package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ExpenseCategoryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import org.springframework.stereotype.Component
import java.util.*

@Component
class ExpenseCategorySpecificationBuilder : SpecificationBuilder<ExpenseCategory, ExpenseCategoryDatabaseModel>() {

    override val filterPredicates = filterPredicates<ExpenseCategory, ExpenseCategoryDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        boolean("active") { root, _, _ -> root.get("active") }
    }
}