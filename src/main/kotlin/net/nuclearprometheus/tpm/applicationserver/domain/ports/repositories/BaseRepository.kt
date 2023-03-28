package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories

interface BaseRepository<TEntity, TEntityId> {
    fun getAll(): List<TEntity>
    fun get(id: TEntityId): TEntity?
    fun get(ids: List<TEntityId>): List<TEntity>
    fun create(project: TEntity): TEntity
    fun update(project: TEntity): TEntity
    fun delete(id: TEntityId)
}
