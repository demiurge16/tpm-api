package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class ThreadSpecificationBuilder : SpecificationBuilder<Thread, ThreadDatabaseModel>() {

    override val filterPredicates = filterPredicates<ThreadDatabaseModel> {
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
        field("title") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("title"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("title"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                root.get<String>("title").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = value as List<String>
                criteriaBuilder.not(root.get<String>("title").`in`(values))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("title"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.equal(root.get<String>("title"), "")
            }
        }
        field("createdAt") {
            eq { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.equal(root.get<ZonedDateTime>("createdAt"), createdAt)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThan(root.get("createdAt"), createdAt)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt)
            }
            lessThan { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThan(root.get("createdAt"), createdAt)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                val createdAt = ZonedDateTime.parse(value as String)
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAt)
            }
            any { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { ZonedDateTime.parse(it) }
                root.get<ZonedDateTime>("createdAt").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { ZonedDateTime.parse(it) }
                criteriaBuilder.not(root.get<ZonedDateTime>("createdAt").`in`(values))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("createdAt"))
            }
        }
        field("status") {
            eq { criteriaBuilder, _, root, value ->
                val status = ThreadStatusDatabaseModel.valueOf(value as String)
                criteriaBuilder.equal(root.get<ThreadStatusDatabaseModel>("status"), status)
            }
            any { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { ThreadStatusDatabaseModel.valueOf(it) }
                root.get<ThreadStatusDatabaseModel>("status").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { ThreadStatusDatabaseModel.valueOf(it) }
                criteriaBuilder.not(root.get<ThreadStatusDatabaseModel>("status").`in`(values))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<ThreadStatusDatabaseModel>("status"))
            }
        }
        field("authorId") {
            eq { criteriaBuilder, _, root, value ->
                val authorId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("authorId"), authorId)
            }
            any { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("authorId").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("authorId").`in`(values))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<UUID>("authorId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                val projectId = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("projectId"), projectId)
            }
            any { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { UUID.fromString(it) }
                root.get<UUID>("projectId").`in`(values)
            }
            none { criteriaBuilder, _, root, value ->
                val values = (value as List<String>).map { UUID.fromString(it) }
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(values))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<UUID>("projectId"))
            }
        }
        field("tags") {
            all { criteriaBuilder, query, root, value ->
                val tags = value as List<String>
                val subquery = query.subquery(Long::class.java)
                val subRoot = subquery.from(TagDatabaseModel::class.java)
                subquery.select(criteriaBuilder.count(subRoot))
                    .where(
                        criteriaBuilder.and(
                            criteriaBuilder.equal(subRoot.get<UUID>("threadId"), root.get<UUID>("id")),
                            subRoot.get<String>("name").`in`(tags)
                        )
                    )
                criteriaBuilder.equal(subquery, tags.size.toLong())
            }
            any { criteriaBuilder, query, root, value ->
                val tags = value as List<String>
                val subquery = query.subquery(Long::class.java)
                val subRoot = subquery.from(TagDatabaseModel::class.java)
                subquery.select(criteriaBuilder.count(subRoot))
                    .where(
                        criteriaBuilder.and(
                            criteriaBuilder.equal(subRoot.get<UUID>("threadId"), root.get<UUID>("id")),
                            subRoot.get<String>("name").`in`(tags)
                        )
                    )
                criteriaBuilder.greaterThan(subquery, 0L)
            }
            none { criteriaBuilder, query, root, value ->
                val tags = value as List<String>
                val subquery = query.subquery(Long::class.java)
                val subRoot = subquery.from(TagDatabaseModel::class.java)
                subquery.select(criteriaBuilder.count(subRoot))
                    .where(
                        criteriaBuilder.and(
                            criteriaBuilder.equal(subRoot.get<UUID>("threadId"), root.get<UUID>("id")),
                            subRoot.get<String>("name").`in`(tags)
                        )
                    )
                criteriaBuilder.equal(subquery, 0L)
            }
            isNull { criteriaBuilder, query, root, value ->
                val subquery = query.subquery(Long::class.java)
                val subRoot = subquery.from(TagDatabaseModel::class.java)
                subquery.select(criteriaBuilder.count(subRoot))
                    .where(
                        criteriaBuilder.equal(subRoot.get<UUID>("threadId"), root.get<UUID>("id"))
                    )
                criteriaBuilder.equal(subquery, 0L)
            }
            isEmpty { criteriaBuilder, query, root, value ->
                val subquery = query.subquery(Long::class.java)
                val subRoot = subquery.from(TagDatabaseModel::class.java)
                subquery.select(criteriaBuilder.count(subRoot))
                    .where(
                        criteriaBuilder.equal(subRoot.get<UUID>("threadId"), root.get<UUID>("id"))
                    )
                criteriaBuilder.equal(subquery, 0L)
            }
        }
    }
}