package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TagSpecificationBuilder : SpecificationBuilder<Tag, TagDatabaseModel>() {

    override val filterPredicates = filterPredicates<TagDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                val id = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), id)
            }
            any { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("id").`in`(ids)
            }
            none { criteriaBuilder, _, root, value ->
                val ids = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("id").`in`(ids))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        }
        field("name") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("name"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                root.get<String>("name").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<String>("name"), "")
            }
        }
        field("threadId") {
            eq { criteriaBuilder, _, root, value ->
                val threadId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("threadId"), threadId)
            }
            any { criteriaBuilder, _, root, value ->
                val threadIds = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("threadId").`in`(threadIds)
            }
            none { criteriaBuilder, _, root, value ->
                val threadIds = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("threadId").`in`(threadIds))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<UUID>("threadId"))
            }
        }
    }
}