package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadLikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLike
import org.springframework.stereotype.Component

@Component
class ThreadLikeSpecificationBuilder : SpecificationBuilder<ThreadLike, ThreadLikeDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ThreadLikeDatabaseModel>>> = mapOf()
}
