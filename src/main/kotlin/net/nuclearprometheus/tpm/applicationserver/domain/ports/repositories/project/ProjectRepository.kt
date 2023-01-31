package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId


interface ProjectRepository {
    fun getAll(): List<Project>
    fun get(id: ProjectId): Project
    fun create(project:Project): Project
    fun update(project: Project): Project
    fun delete(id: ProjectId)
}