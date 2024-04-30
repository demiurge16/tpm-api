package net.nuclearprometheus.tpm.applicationserver.domain.model.expense.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.Date
import java.util.UUID

object ExpenseSpecificationBuilder : SpecificationBuilder<Expense>() {
    val id = uniqueValue("id", UUID::class)
    val amount = comparable("amount", Double::class)
    val date = comparable("date", Date::class)
    val categoryId = uniqueValue("categoryId", UUID::class)
    val spenderId = uniqueValue("spenderId", UUID::class)
    val projectId = uniqueValue("projectId", UUID::class)
}
