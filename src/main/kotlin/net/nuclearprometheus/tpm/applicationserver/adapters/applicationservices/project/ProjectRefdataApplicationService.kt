package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectRefdataMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ProjectRefdataApplicationService {

    private val logger = loggerFor(ProjectRefdataApplicationService::class.java)

    fun getStatuses() = with(logger) {
        info("getStatuses")

        ProjectStatus.values().map { it.toView() }
    }

    fun getTeamMemberRoles() = with(logger) {
        info("getTeamMemberRoles")

        ProjectRole.values().map { it.toView() }
    }
}