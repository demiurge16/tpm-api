package net.nuclearprometheus.tpm.applicationserver.domain.model.common

open class Entity<TId>(val id: TId) where TId : Id<*>
