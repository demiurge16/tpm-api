package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Component
class ProjectSpecificationBuilder : SpecificationBuilder<Project, ProjectDatabaseModel>() {

    override val filterPredicates = filterPredicates<Project, ProjectDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("title") { root, _, _ -> root.get("title") }
        uniqueValue("sourceLanguage") { root, _, _ -> root.get<String>("sourceLanguage") }
        collection("targetLanguages") { root, _, _ -> root.get<List<String>>("targetLanguages") }
        uniqueValue("accuracyId") { root, _, _ ->
            val accuracy = root.join<ProjectDatabaseModel, AccuracyDatabaseModel>("accuracy")
            accuracy.get<UUID>("id")
        }
        uniqueValue("industryId") { root, _, _ ->
            val industry = root.join<ProjectDatabaseModel, IndustryDatabaseModel>("industry")
            industry.get<UUID>("id")
        }
        uniqueValue("unitId") { root, _, _ ->
            val unit = root.join<ProjectDatabaseModel, UnitDatabaseModel>("unit")
            unit.get<UUID>("id")
        }
        comparable("amount") { root, _, _ -> root.get<Int>("amount") }
        comparable("expectedStart") { root, _, _ -> root.get<ZonedDateTime>("expectedStart") }
        comparable("internalDeadline") { root, _, _ -> root.get<ZonedDateTime>("internalDeadline") }
        comparable("externalDeadline") { root, _, _ -> root.get<ZonedDateTime>("externalDeadline") }
        comparable("budget") { root, _, _ -> root.get<BigDecimal>("budget") }
        uniqueValue("currency") { root, _, _ -> root.get<String>("currency") }
        enum("status") { root, _, _ -> root.get<ProjectStatusDatabaseModel>("status") }
        uniqueValue("clientId") { root, _, _ ->
            val client = root.join<ProjectDatabaseModel, ClientDatabaseModel>("client")
            client.get<UUID>("id")
        }
    }
}
