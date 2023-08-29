package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

fun <TDatabaseModel> filterPredicates(init: FilterPredicatesBuilder<TDatabaseModel>.() -> Unit): FilterPredicates<TDatabaseModel> {
    val builder = FilterPredicatesBuilder<TDatabaseModel>()
    builder.init()
    return builder.build()
}

class FilterPredicatesBuilder<TDatabaseModel>(
    private val filters: MutableMap<String, Map<String, PredicateSupplier<TDatabaseModel>>> = mutableMapOf()
) {

    fun field(field: String, init: FieldPredicateBuilder<TDatabaseModel>.() -> Unit) {
        val builder = FieldPredicateBuilder<TDatabaseModel>()
        builder.init()
        filters[field] = builder.build()
    }

    fun build(): FilterPredicates<TDatabaseModel> = FilterPredicates(filters)
}

class FilterPredicates<TDatabaseModel>(
    private val predicates: Map<String, Map<String, PredicateSupplier<TDatabaseModel>>>
) {

    operator fun get(field: String, filter: String): PredicateSupplier<TDatabaseModel> = predicates[field]?.get(filter)
        ?: throw IllegalArgumentException("No filter predicate for field '$field' and filter '$filter'")
}

