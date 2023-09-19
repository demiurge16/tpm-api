package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRoleId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TeamMemberRoleRepository : BaseRepository<TeamMemberRole, TeamMemberRoleId>
