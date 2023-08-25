package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import org.springframework.stereotype.Component

@Component
class ExpenseSpecificationBuilder : SpecificationBuilder<Expense, ExpenseDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ExpenseDatabaseModel>>> = mapOf()
}