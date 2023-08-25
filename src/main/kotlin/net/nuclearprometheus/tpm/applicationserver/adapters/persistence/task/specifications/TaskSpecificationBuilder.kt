package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.PredicateSupplier
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import org.springframework.stereotype.Component

@Component
class TaskSpecificationBuilder : SpecificationBuilder<Task, TaskDatabaseModel>() {

    override val filters: Map<String, Map<String, PredicateSupplier<TaskDatabaseModel>>> = mapOf()
}