package net.nuclearprometheus.tpm.applicationserver.config.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.TeamMemberService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.TeamMemberServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRoleRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class TeamMemberConfig(
    private val teamMemberRoleRepository: TeamMemberRoleRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository
) {

    @Bean
    fun teamMemberService(): TeamMemberService = TeamMemberServiceImpl(
        teamMemberRoleRepository,
        teamMemberRepository,
        userRepository,
        userContextProvider,
        projectRepository,
        loggerFor(TeamMemberService::class.java)
    )
}
