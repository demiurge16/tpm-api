package net.nuclearprometheus.tpm.applicationserver.queries.operations.logical

class OrOperation<TEntity : Any> : LogicalOperation<TEntity> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode() = javaClass.hashCode()
}
