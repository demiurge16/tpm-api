package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeEntryStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TimeUnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class TimeEntrySpecificationBuilder : SpecificationBuilder<TimeEntry, TimeEntryDatabaseModel>() {

    override val filterPredicates = filterPredicates<TimeEntry, TimeEntryDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("description") { root, _, _ -> root.get("description") }
        comparable("date") { root, _, _ -> root.get<LocalDate>("date") }
        comparable("timeSpent") { root, _, _ -> root.get<Int>("timeSpent") }
        enum("timeUnit") { root, _, _ -> root.get<TimeUnitDatabaseModel>("timeUnit") }
        enum("status") { root, _, _ -> root.get<TimeEntryStatusDatabaseModel>("status") }
        uniqueValue("taskId") { root, _, _ -> root.get<UUID>("taskId") }
    }
}