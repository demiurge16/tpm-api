package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.*

fun <TEntity : Any> where(init: () -> OperationNode<TEntity>): Search<TEntity> {
    return Search(init())
}

fun <TEntity : Any> not(node: OperationNode<TEntity>): OperationNode<TEntity> {
    return Not(node)
}

fun <TEntity : Any> not(init: () -> OperationNode<TEntity>): OperationNode<TEntity> {
    return Not(init())
}

infix fun <TEntity : Any> OperationNode<TEntity>.and(node: OperationNode<TEntity>): OperationNode<TEntity> {
    return And(this, node)
}

infix fun <TEntity : Any> OperationNode<TEntity>.and(init: () -> OperationNode<TEntity>): OperationNode<TEntity> {
    return And(this, init())
}

infix fun <TEntity : Any> OperationNode<TEntity>.or(node: OperationNode<TEntity>): OperationNode<TEntity> {
    return Or(this, node)
}

infix fun <TEntity : Any> OperationNode<TEntity>.or(init: () -> OperationNode<TEntity>): OperationNode<TEntity> {
    return Or(this, init())
}

inline fun <TEntity : Any, reified TValue : Any> uniqueToken(noinline valueGetter: ValueGetter<TEntity, TValue?>): UniqueToken<TEntity, TValue> {
    return UniqueToken(valueGetter, TValue::class)
}

fun <TEntity : Any> string(valueGetter: ValueGetter<TEntity, String?>): StringToken<TEntity> {
    return StringToken(valueGetter)
}

inline fun <TEntity : Any, reified TValue : Comparable<TValue>> comparable(noinline valueGetter: ValueGetter<TEntity, TValue?>): ComparableToken<TEntity, TValue> {
    return ComparableToken(valueGetter, TValue::class)
}

fun <TEntity : Any> boolean(valueGetter: ValueGetter<TEntity, Boolean?>): BooleanToken<TEntity> {
    return BooleanToken(valueGetter)
}

inline fun <TEntity : Any, reified TValue : Any> collection(noinline valueGetter: ValueGetter<TEntity, Collection<TValue>?>): CollectionToken<TEntity, TValue> {
    return CollectionToken(valueGetter, TValue::class)
}

inline fun <TEntity : Any, reified TValue : Enum<TValue>> enum(noinline valueGetter: ValueGetter<TEntity, TValue?>): EnumToken<TEntity, TValue> {
    return EnumToken(valueGetter, TValue::class)
}
