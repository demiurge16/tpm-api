package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import org.springframework.stereotype.Component

@Component
class ThreadSpecificationBuilder : SpecificationBuilder<Thread, ThreadDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ThreadDatabaseModel>>> = mapOf()
}