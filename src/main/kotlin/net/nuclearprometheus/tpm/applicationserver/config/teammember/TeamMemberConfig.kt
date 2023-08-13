package net.nuclearprometheus.tpm.applicationserver.config.teammember

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.teammember.TeamMemberServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class TeamMemberConfig(
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository
) {

    @Bean
    fun teamMemberService(): TeamMemberService = TeamMemberServiceImpl(
        teamMemberRepository,
        userRepository,
        projectRepository,
        loggerFor(TeamMemberService::class.java)
    )
}
