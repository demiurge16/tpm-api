package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.ProjectRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeamMemberRoleSpecificationBuilder : SpecificationBuilder<TeamMemberRole, TeamMemberRoleDatabaseModel>() {

    override val filterPredicates = filterPredicates<TeamMemberRoleDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                val id = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), id)
            }
            any { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("id").`in`(ids)
            }
            none { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("id").`in`(ids))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        }
        field("role") {
            eq { criteriaBuilder, _, root, value ->
                val role = ProjectRoleDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<ProjectRoleDatabaseModel>("role"), role)
            }
            any { criteriaBuilder, _, root, value ->
                val roles = (value as List<String>).map { ProjectRoleDatabaseModel.valueOf(it) }
                root.get<ProjectRoleDatabaseModel>("role").`in`(roles)
            }
            none { criteriaBuilder, _, root, value ->
                val roles = (value as List<String>).map { ProjectRoleDatabaseModel.valueOf(it) }
                criteriaBuilder.not(root.get<ProjectRoleDatabaseModel>("role").`in`(roles))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ProjectRoleDatabaseModel>("role"))
            }
        }
        field("userId") {
            eq { criteriaBuilder, _, root, value ->
                val userId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("userId"), userId)
            }
            any { criteriaBuilder, _, root, value ->
                val userIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("userId").`in`(userIds)
            }
            none { criteriaBuilder, _, root, value ->
                val userIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("userId").`in`(userIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("userId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                val projectId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("projectId"), projectId)
            }
            any { criteriaBuilder, _, root, value ->
                val projectIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("projectId").`in`(projectIds)
            }
            none { criteriaBuilder, _, root, value ->
                val projectIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(projectIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
    }
}