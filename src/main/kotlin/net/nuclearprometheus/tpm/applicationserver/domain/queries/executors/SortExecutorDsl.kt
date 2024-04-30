package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

fun <TEntity : Any> sortExecutors(init: SortExecutorsBuilder<TEntity>.() -> Unit): SortExecutors<TEntity> {
    val builder = SortExecutorsBuilder<TEntity>()
    builder.init()
    return SortExecutors(builder.build())
}

class SortExecutorsBuilder<TEntity : Any> {
    private val executors: MutableMap<String, Comparator<TEntity>> = mutableMapOf()

    fun sort(name: String, comparator: Comparator<TEntity>) {
        executors[name] = comparator
    }

    fun sort(name: String, vararg comparators: Comparator<TEntity>) {
        executors[name] = comparators.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }

    fun build(): Map<String, Comparator<TEntity>> = executors
}
