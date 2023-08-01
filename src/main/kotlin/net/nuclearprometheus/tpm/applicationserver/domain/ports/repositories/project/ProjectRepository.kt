package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ProjectRepository : BaseRepository<Project, ProjectId>
