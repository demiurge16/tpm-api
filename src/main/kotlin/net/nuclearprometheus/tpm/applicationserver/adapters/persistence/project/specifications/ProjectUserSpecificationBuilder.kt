package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProjectUserSpecificationBuilder {

    fun build(userId: UserId): Specification<ProjectDatabaseModel> {
        return Specification { root, criteriaQuery, criteriaBuilder ->
            val teamMembers = root.join<ProjectDatabaseModel, TeamMemberRoleDatabaseModel>("teamMembers")
            criteriaBuilder.equal(teamMembers.get<UUID>("userId"), userId.value)
        }
    }
}
