package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface ProjectRepository : BaseRepository<Project, ProjectId> {

    fun getProjectsForUser(userId: UserId, query: Query<Project>): Page<Project>
}
