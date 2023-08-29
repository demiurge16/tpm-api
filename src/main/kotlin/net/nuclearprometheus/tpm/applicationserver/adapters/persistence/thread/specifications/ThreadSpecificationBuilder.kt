package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.*
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.UUID

@Component
class ThreadSpecificationBuilder : SpecificationBuilder<Thread, ThreadDatabaseModel>() {

    override val filterPredicates = filterPredicates<ThreadDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("id").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
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
                criteriaBuilder.like(root.get<String>("title"), "%$value%")
            }
            any{ criteriaBuilder, _, root, value ->
                root.get<String>("title").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("title").`in`(value))
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
                criteriaBuilder.equal(root.get<ZonedDateTime>("createdAt"), value)
            }
            greaterThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThan(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            greaterThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.greaterThanOrEqualTo(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            lessThan { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThan(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            lessThanOrEqualTo { criteriaBuilder, _, root, value ->
                criteriaBuilder.lessThanOrEqualTo(root.get<ZonedDateTime>("createdAt"), value as ZonedDateTime)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ZonedDateTime>("createdAt").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ZonedDateTime>("createdAt").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<ZonedDateTime>("createdAt"))
            }
        }
        field("status") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<ThreadStatusDatabaseModel>("status"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<ThreadStatusDatabaseModel>("status").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<ThreadStatusDatabaseModel>("status").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<ThreadStatusDatabaseModel>("status"))
            }
        }
        field("authorId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("authorId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("authorId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("authorId").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<UUID>("authorId"))
            }
        }
        field("projectId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("projectId"), value)
            }
            any { criteriaBuilder, _, root, value ->
                root.get<UUID>("projectId").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("projectId").`in`(value))
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