package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TeamMemberSpecificationBuilder : SpecificationBuilder<TeamMember, TeamMemberDatabaseModel>() {

    override val filterPredicates = filterPredicates<TeamMemberDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("id").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        }
        field("role") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<TeamMemberRoleDatabaseModel>("role"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<TeamMemberRoleDatabaseModel>("role").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<TeamMemberRoleDatabaseModel>("role").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<TeamMemberRoleDatabaseModel>("role"))
            }
        }
        field("userId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("userId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("userId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("userId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("userId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("projectId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("projectId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
    }
}