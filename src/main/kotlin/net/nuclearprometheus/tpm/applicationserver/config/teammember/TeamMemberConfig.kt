package net.nuclearprometheus.tpm.applicationserver.config.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security.ProjectPermissionService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRoleRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class TeamMemberConfig(
    private val teamMemberRoleRepository: TeamMemberRoleRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val projectPermissionService: ProjectPermissionService
) {

    @Bean
    fun teamMemberService(): TeamMemberService = TeamMemberServiceImpl(
        teamMemberRoleRepository,
        teamMemberRepository,
        userRepository,
        projectRepository,
        projectPermissionService,
        loggerFor(TeamMemberService::class.java)
    )
}
