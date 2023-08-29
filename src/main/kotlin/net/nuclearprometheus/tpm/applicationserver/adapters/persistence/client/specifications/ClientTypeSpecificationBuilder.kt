package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClientTypeSpecificationBuilder : SpecificationBuilder<ClientType, ClientTypeDatabaseModel>() {

    override val filterPredicates = filterPredicates<ClientTypeDatabaseModel> {
        field("id") {
            eq { criteriaBuilder, _, root, value ->
                val uuid = UUID.fromString(value as String)
                criteriaBuilder.equal(root.get<UUID>("id"), uuid)
            }
            any { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                root.get<UUID>("id").`in`(list.map { UUID.fromString(it) })
            }
            none { criteriaBuilder, _, root, value ->
                val list = value as List<String>
                criteriaBuilder.not(root.get<UUID>("id").`in`(list.map { UUID.fromString(it) }))
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
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("name").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
            isEmpty { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        }
        field("corporate") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Boolean>("corporate"), (value as String).toBoolean())
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Boolean>("corporate"))
            }
        }
        field("active") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Boolean>("active"), (value as String).toBoolean())
            }
            isNull { criteriaBuilder, _, root, _ ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        }
    }
}