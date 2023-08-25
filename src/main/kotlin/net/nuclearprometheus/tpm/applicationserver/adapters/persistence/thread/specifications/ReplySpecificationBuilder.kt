package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import org.springframework.stereotype.Component

@Component
class ReplySpecificationBuilder : SpecificationBuilder<Reply, ReplyDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ReplyDatabaseModel>>> = mapOf()
}
