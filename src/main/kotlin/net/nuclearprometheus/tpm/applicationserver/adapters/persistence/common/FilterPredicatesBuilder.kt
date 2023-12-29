package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.predicates.*

fun <TEntity : Any, TDatabaseModel : Any> filterPredicates(init: FilterPredicatesBuilder<TEntity, TDatabaseModel>.() -> Unit): FilterPredicates<TEntity, TDatabaseModel> {
    val builder = FilterPredicatesBuilder<TEntity, TDatabaseModel>()
    builder.init()
    return builder.build()
}

class FilterPredicatesBuilder<TEntity : Any, TDatabaseModel : Any>(
    private val filters: MutableMap<String, PredicateFactory<TEntity, TDatabaseModel>> = mutableMapOf()
) {

    fun <TValue : Any> uniqueValue(field: String, expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<TValue>) {
        filters[field] = UniqueValuePredicateFactory(expressionSupplier)
    }

    fun string(field: String, expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<String>) {
        filters[field] = StringPredicateFactory(expressionSupplier)
    }

    fun <TValue : Comparable<TValue>> comparable(field: String, expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<TValue>) {
        filters[field] = ComparablePredicateFactory(expressionSupplier)
    }

    fun <TValue : Any> collection(field: String, expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<out Collection<TValue>>) {
        filters[field] = CollectionPredicateFactory(expressionSupplier)
    }

    fun  boolean(field: String, expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<Boolean>) {
        filters[field] = BooleanPredicateFactory(expressionSupplier)
    }

    fun <TValue : Enum<TValue>> enum(field: String, expressionSupplier: (Root<TDatabaseModel>, CriteriaQuery<*>, CriteriaBuilder) -> Expression<TValue>) {
        filters[field] = EnumPredicateFactory(expressionSupplier)
    }

    fun build(): FilterPredicates<TEntity, TDatabaseModel> = FilterPredicates(filters)
}

class FilterPredicates<TEntity : Any, TDatabaseModel>(
    private val predicates: Map<String, PredicateFactory<TEntity, TDatabaseModel>>
) {

    operator fun get(field: String): PredicateFactory<TEntity, TDatabaseModel> = predicates[field]
        ?: throw IllegalArgumentException("No filter predicate for field '$field'")
}

