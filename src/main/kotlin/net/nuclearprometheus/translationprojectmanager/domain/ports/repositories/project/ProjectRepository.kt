package net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.project

import net.nuclearprometheus.translationprojectmanager.domain.model.project.Project
import net.nuclearprometheus.translationprojectmanager.domain.model.project.ProjectId


interface ProjectRepository {
    fun getAll(): List<Project>
    fun get(id: ProjectId): Project
    fun create(project:Project): Project
    fun update(project: Project): Project
    fun delete(id: ProjectId)
}