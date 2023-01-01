package net.nuclearprometheus.translationprojectmanager.queries.operations.binary

import net.nuclearprometheus.translationprojectmanager.queries.operations.ComparisonOperation

interface BinaryComparisonOperation<TEntity : Any, TValue> : ComparisonOperation<TEntity> {
    val value: TValue
}
