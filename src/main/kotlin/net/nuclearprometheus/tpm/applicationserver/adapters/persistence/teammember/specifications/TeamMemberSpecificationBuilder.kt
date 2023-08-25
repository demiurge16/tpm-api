package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import org.springframework.stereotype.Component

@Component
class TeamMemberSpecificationBuilder : SpecificationBuilder<TeamMember, TeamMemberDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<TeamMemberDatabaseModel>>> = mapOf()
}