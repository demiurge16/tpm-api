package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ProjectRefdataMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class ProjectRefdataApplicationService {

    private val logger = loggerFor(ProjectRefdataApplicationService::class.java)

    fun getStatuses() = with(logger) {
        info("getStatuses")

        ProjectStatus.values().map { it.toView() }
    }

}