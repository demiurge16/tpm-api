package net.nuclearprometheus.tpm.applicationserver.domain.model.common

open class Id<T>(val value: T): Comparable<Id<T>> where T : Comparable<T>, T : Any {
    override fun compareTo(other: Id<T>) = value.compareTo(other.value)
    override fun equals(other: Any?) = other is Id<*> && value == other.value
    override fun hashCode() = value.hashCode()
    override fun toString() = value.toString()
}

