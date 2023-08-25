package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import org.springframework.stereotype.Component

@Component
class ProjectSpecificationBuilder : SpecificationBuilder<Project, ProjectDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<ProjectDatabaseModel>>> = mapOf()
}
