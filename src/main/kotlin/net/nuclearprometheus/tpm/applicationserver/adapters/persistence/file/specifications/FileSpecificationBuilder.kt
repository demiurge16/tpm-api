package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import org.springframework.stereotype.Component

@Component
class FileSpecificationBuilder : SpecificationBuilder<File, FileDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<FileDatabaseModel>>> = mapOf()
}