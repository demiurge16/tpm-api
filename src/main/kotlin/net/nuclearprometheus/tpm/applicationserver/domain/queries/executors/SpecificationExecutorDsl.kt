package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

fun <TEntity : Any> specificationExecutors(init: SpecificationExecutorsBuilder<TEntity>.() -> Unit): SpecificationExecutors<TEntity> {
    val builder = SpecificationExecutorsBuilder<TEntity>()
    builder.init()
    return builder.build()
}

class SpecificationExecutorsBuilder<TEntity : Any>(
    private val executors: MutableMap<String, SpecificationExecutor<TEntity, *>> = mutableMapOf()
) {

    fun <TValue : Any> uniqueValue(name: String, valueProvider: ValueGetter<TEntity, TValue>) {
        executors[name] = UniqueValueSpecificationExecutor(valueProvider)
    }

    fun string(name: String, valueProvider: ValueGetter<TEntity, String>) {
        executors[name] = StringSpecificationExecutor(valueProvider)
    }

    fun <TValue : Comparable<TValue>> comparable(name: String, valueProvider: ValueGetter<TEntity, TValue>) {
        executors[name] = ComparableSpecificationExecutor(valueProvider)
    }

    fun <TValue : Any> collection(name: String, valueProvider: ValueGetter<TEntity, Collection<TValue>>) {
        executors[name] = CollectionSpecificationExecutor(valueProvider)
    }

    fun boolean(name: String, valueProvider: ValueGetter<TEntity, Boolean>) {
        executors[name] = BooleanSpecificationExecutor(valueProvider)
    }

    fun <TValue : Enum<TValue>> enum(name: String, valueProvider: ValueGetter<TEntity, TValue>) {
        executors[name] = EnumSpecificationExecutor(valueProvider)
    }

    fun build(): SpecificationExecutors<TEntity> = SpecificationExecutors(executors)
}
