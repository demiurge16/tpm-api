package net.nuclearprometheus.tpm.applicationserver.queries.operations.binary

import net.nuclearprometheus.tpm.applicationserver.queries.operations.ComparisonOperation

interface BinaryComparisonOperation<TEntity : Any, TValue> : ComparisonOperation<TEntity> {
    val value: TValue
}
