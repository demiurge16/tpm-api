package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.util.*

@Component
class TimeEntryTaskSpecificationBuilder {
    fun create(taskId: TaskId): Specification<TimeEntryDatabaseModel> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<UUID>("taskId"), taskId.value)
        }
    }
}