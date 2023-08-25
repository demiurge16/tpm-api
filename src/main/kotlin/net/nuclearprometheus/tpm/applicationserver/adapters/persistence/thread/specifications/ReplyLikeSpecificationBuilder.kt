package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyLikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyLike
import org.springframework.stereotype.Component

@Component
class ReplyLikeSpecificationBuilder : SpecificationBuilder<ReplyLike, ReplyLikeDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ReplyLikeDatabaseModel>>> = mapOf()
}