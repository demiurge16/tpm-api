package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId

interface BaseRepository<TEntity, TEntityId> {
    fun getAll(): List<TEntity>
    fun get(id: ProjectId): TEntity?
    fun create(project: Project): TEntity
    fun update(project: Project): TEntity
    fun delete(id: ProjectId)
}
