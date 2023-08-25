package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag
import org.springframework.stereotype.Component

@Component
class TagSpecificationBuilder : SpecificationBuilder<Tag, TagDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<TagDatabaseModel>>> = mapOf()
}