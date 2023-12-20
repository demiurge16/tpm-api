package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

fun <TEntity : Any, TValue : Any> uniqueValue(name: String, valueProvider: ValueGetter<TEntity, TValue>): Pair<String, SpecificationExecutor<TEntity, TValue>> {
    return name to UniqueValueSpecificationExecutor(valueProvider)
}

fun <TEntity : Any> string(name: String, valueProvider: ValueGetter<TEntity, String>): Pair<String, SpecificationExecutor<TEntity, String>> {
    return name to StringSpecificationExecutor(valueProvider)
}

fun <TEntity : Any, TValue : Comparable<TValue>> comparable(name: String, valueProvider: ValueGetter<TEntity, TValue>): Pair<String, SpecificationExecutor<TEntity, TValue>> {
    return name to ComparableSpecificationExecutor(valueProvider)
}

fun <TEntity : Any, TValue : Any> collection(name: String, valueProvider: ValueGetter<TEntity, Collection<TValue>>): Pair<String, SpecificationExecutor<TEntity, Collection<TValue>>> {
    return name to CollectionSpecificationExecutor(valueProvider)
}

fun <TEntity : Any> boolean(name: String, valueProvider: ValueGetter<TEntity, Boolean>): Pair<String, SpecificationExecutor<TEntity, Boolean>> {
    return name to BooleanSpecificationExecutor(valueProvider)
}

fun <TEntity : Any, TValue : Enum<TValue>> enum(name: String, valueProvider: ValueGetter<TEntity, TValue>): Pair<String, SpecificationExecutor<TEntity, TValue>> {
    return name to EnumSpecificationExecutor(valueProvider)
}
