package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadDislike
import org.springframework.stereotype.Component

@Component
class ThreadDislikeSpecificationBuilder : SpecificationBuilder<ThreadDislike, ThreadDislikeDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ThreadDislikeDatabaseModel>>> = mapOf()
}