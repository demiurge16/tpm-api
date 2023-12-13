package net.nuclearprometheus.tpm.applicationserver.domain.model.project.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object TeamMemberRoleSpecificationBuilder : SpecificationBuilder<TeamMember>() {
    val id = uniqueValue("id", UUID::class)
    val role = enum("role", ProjectRole::class)
    val userId = uniqueValue("userId", UUID::class)
    val projectId = uniqueValue("projectId", UUID::class)
}