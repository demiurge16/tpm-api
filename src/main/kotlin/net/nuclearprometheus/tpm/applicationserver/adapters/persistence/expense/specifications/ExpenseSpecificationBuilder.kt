package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ExpenseCategoryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Component
class ExpenseSpecificationBuilder : SpecificationBuilder<Expense, ExpenseDatabaseModel>() {

    override val filterPredicates = filterPredicates<ExpenseDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                val uuid = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), uuid)
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<UUID>("id").`in`(list.map { UUID.fromString(it) })
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<UUID>("id").`in`(list.map { UUID.fromString(it) }))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        }
        field("amount") {
            eq { criteriaBuilder, _, root, value ->
                val amount = (value as String).toBigDecimal()
                criteriaBuilder.equal(root.get<BigDecimal>("amount"), amount)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val amount = (value as String).toBigDecimal()
                criteriaBuilder.greaterThan(root.get("amount"), amount)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val amount = (value as String).toBigDecimal()
                criteriaBuilder.lessThan(root.get("amount"), amount)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val amount = (value as String).toBigDecimal()
                criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amount)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val amount = (value as String).toBigDecimal()
                criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amount)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toBigDecimal() }
                root.get<BigDecimal>("amount").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { it.toBigDecimal() }
                criteriaBuilder.not(root.get<BigDecimal>("amount").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<BigDecimal>("amount"))
            }
        }
        field("currency") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("currency"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("currency").`in`(value as List<String>)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("currency").`in`(value as List<String>))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("currency"))
            }
        }
        field("date") {
            eq { criteriaBuilder, _, root, value ->
                val date = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("date"), date)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val date = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("date"), date)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val date = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("date"), date)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val date = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val date = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("date"), date)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("date").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("date").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("date"))
            }
        }
        field("categoryId") {
            eq { criteriaBuilder, _, root, value ->
                val uuid = UUID.fromString(value as String)
                val category = root.join<ExpenseDatabaseModel, ExpenseCategoryDatabaseModel>("category")
                criteriaBuilder.equal(category.get<UUID>("id"), uuid)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                val category = root.join<ExpenseDatabaseModel, ExpenseCategoryDatabaseModel>("category")
                category.get<UUID>("id").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                val category = root.join<ExpenseDatabaseModel, ExpenseCategoryDatabaseModel>("category")
                criteriaBuilder.not(category.get<UUID>("id").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                val category = root.join<ExpenseDatabaseModel, ExpenseCategoryDatabaseModel>("category")
                criteriaBuilder.isNull(category.get<UUID>("id"))
            }
        }
        field("spenderId") {
            eq { criteriaBuilder, _, root, value ->
                val uuid = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("spenderId"), uuid)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("spenderId").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("spenderId").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("spenderId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                val uuid = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("projectId"), uuid)
            }
            any { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("projectId").`in`(list)
            }
            none { criteriaBuilder, _, root, value ->
                val list = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(list))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
    }
}