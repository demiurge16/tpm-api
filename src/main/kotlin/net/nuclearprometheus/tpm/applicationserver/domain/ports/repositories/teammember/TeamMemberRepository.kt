package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TeamMemberRepository : BaseRepository<TeamMember, TeamMemberId>
