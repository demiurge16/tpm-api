package net.nuclearprometheus.tpm.applicationserver.domain.model.project.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object TeamMemberSpecificationBuilder : SpecificationBuilder<TeamMember>() {
    val userId = uniqueValue("userId", UUID::class)
    val projectId = uniqueValue("projectId", UUID::class)
}