package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberRoleId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TeamMemberRoleRepository : BaseRepository<TeamMemberRole, TeamMemberRoleId>
