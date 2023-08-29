package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common

fun <TDatabaseModel> field(field: String, init: FieldPredicateBuilder<TDatabaseModel>.() -> Unit): Pair<String, Map<String, PredicateSupplier<TDatabaseModel>>> {
    val builder = FieldPredicateBuilder<TDatabaseModel>()
    builder.init()
    return field to builder.build()
}

class FieldPredicateBuilder<TDatabaseModel>(
    private val filters: MutableMap<String, PredicateSupplier<TDatabaseModel>> = mutableMapOf()
) {

    fun eq(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("eq", predicateSupplier)
    fun contains(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("contains", predicateSupplier)
    fun greaterThan(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("gt", predicateSupplier)
    fun lessThan(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("lt", predicateSupplier)
    fun greaterThanOrEqualTo(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("gte", predicateSupplier)
    fun lessThanOrEqualTo(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("lte", predicateSupplier)
    fun all(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("all", predicateSupplier)
    fun any(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("any", predicateSupplier)
    fun none(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("none", predicateSupplier)
    fun isNull(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("null", predicateSupplier)
    fun isEmpty(predicateSupplier: PredicateSupplier<TDatabaseModel>) = filters.put("empty", predicateSupplier)

    fun build(): Map<String, PredicateSupplier<TDatabaseModel>> = filters
}
