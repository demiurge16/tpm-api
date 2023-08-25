package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface BaseRepository<TEntity : Entity<TEntityId>, TEntityId : Id<*>> {

    fun getAll(): List<TEntity>
    fun get(id: TEntityId): TEntity?
    fun get(ids: List<TEntityId>): List<TEntity>
    fun get(query: Query<TEntity>): Page<TEntity>
    fun create(entity: TEntity): TEntity
    fun createAll(entities: List<TEntity>): List<TEntity>
    fun update(entity: TEntity): TEntity
    fun updateAll(entities: List<TEntity>): List<TEntity>
    fun delete(id: TEntityId)
    fun deleteAll(ids: List<TEntityId>)
}
