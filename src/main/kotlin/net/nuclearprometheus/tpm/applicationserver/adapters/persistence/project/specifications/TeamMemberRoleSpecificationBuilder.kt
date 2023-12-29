package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberRole
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeamMemberRoleSpecificationBuilder : SpecificationBuilder<TeamMemberRole, TeamMemberRoleDatabaseModel>() {

    override val filterPredicates = filterPredicates<TeamMemberRole, TeamMemberRoleDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        enum("role") { root, _, _ -> root.get<ProjectRoleDatabaseModel>("role") }
        uniqueValue("userId") { root, _, _ -> root.get<UUID>("userId") }
        uniqueValue("projectId") { root, _, _ -> root.get<UUID>("projectId") }
    }
}