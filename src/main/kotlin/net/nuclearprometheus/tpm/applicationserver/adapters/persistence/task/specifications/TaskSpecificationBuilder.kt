package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities.TaskStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Component
class TaskSpecificationBuilder : SpecificationBuilder<Task, TaskDatabaseModel>() {

    override val filterPredicates = filterPredicates<Task, TaskDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("title") { root, _, _ -> root.get("title") }
        uniqueValue("sourceLanguage") { root, _, _ -> root.get<String>("sourceLanguage") }
        uniqueValue("targetLanguage") { root, _, _ -> root.get<String>("targetLanguage") }
        uniqueValue("accuracyId") { root, _, _ ->
            val accuracy = root.join<TaskDatabaseModel, AccuracyDatabaseModel>("accuracy")
            accuracy.get<UUID>("id")
        }
        uniqueValue("industryId") { root, _, _ ->
            val industry = root.join<TaskDatabaseModel, IndustryDatabaseModel>("industry")
            industry.get<UUID>("id")
        }
        uniqueValue("unitId") { root, _, _ ->
            val unit = root.join<TaskDatabaseModel, UnitDatabaseModel>("unit")
            unit.get<UUID>("id")
        }
        comparable("amount") { root, _, _ -> root.get<Int>("amount") }
        comparable("expectedStart") { root, _, _ -> root.get<ZonedDateTime>("expectedStart") }
        comparable("deadline") { root, _, _ -> root.get<ZonedDateTime>("internalDeadline") }
        comparable("budget") { root, _, _ -> root.get<BigDecimal>("budget") }
        uniqueValue("currency") { root, _, _ -> root.get<String>("currency") }
        enum("status") { root, _, _ -> root.get<TaskStatusDatabaseModel>("status") }
        uniqueValue("priorityId") { root, _, _ ->
            val priority = root.join<TaskDatabaseModel, PriorityDatabaseModel>("priority")
            priority.get<UUID>("id")
        }
        uniqueValue("assigneeId") { root, _, _ -> root.get<UUID>("assigneeId") }
        uniqueValue("projectId") { root, _, _ -> root.get<UUID>("projectId") }
    }
}