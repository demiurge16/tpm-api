package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class FileSpecificationBuilder : SpecificationBuilder<File, FileDatabaseModel>() {

    override val filterPredicates = filterPredicates<File, FileDatabaseModel> {
        uniqueValue("id") { root, _, _ -> root.get<UUID>("id") }
        string("name") { root, _, _ -> root.get("name") }
        string("type") { root, _, _ -> root.get("type") }
        string("location") { root, _, _ -> root.get("location") }
        comparable("size") { root, _, _ -> root.get<Long>("size") }
        comparable("uploadTime") { root, _, _ -> root.get<ZonedDateTime>("uploadTime") }
        uniqueValue("uploaderId") { root, _, _ -> root.get<UUID>("uploaderId") }
        uniqueValue("projectId") { root, _, _ -> root.get<UUID>("projectId") }
    }
}
