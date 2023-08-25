package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyDislike
import org.springframework.stereotype.Component

@Component
class ReplyDislikeSpecificationBuilder : SpecificationBuilder<ReplyDislike, ReplyDislikeDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ReplyDislikeDatabaseModel>>> = mapOf()
}