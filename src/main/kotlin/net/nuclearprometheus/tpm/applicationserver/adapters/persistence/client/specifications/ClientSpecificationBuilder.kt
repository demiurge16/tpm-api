package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.specifications

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.filterPredicates
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClientSpecificationBuilder : SpecificationBuilder<Client, ClientDatabaseModel>() {

    override val filterPredicates = filterPredicates<ClientDatabaseModel> {
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
            isNull { criteriaBuilder, _, root, value ->
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
            any{ criteriaBuilder, _, root, value ->
                root.get<String>("name").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        }
        field("email") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("email"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("email"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("email").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("email").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("email"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("email"))
            }
        }
        field("phone") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("phone"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("phone"), "%$value%")
            }
            any{ criteriaBuilder, _, root, value ->
                root.get<String>("phone").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("phone").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("phone"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("phone"))
            }
        }
        field("address") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("address"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("address"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("address").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("address").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("address"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("address"))
            }
        }
        field("city") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("city"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("city"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("city").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("city").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("city"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("city"))
            }
        }
        field("state") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("state"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("state"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("state").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("state").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("state"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("state"))
            }
        }
        field("zip") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("zip"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("zip"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("zip").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("zip").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("zip"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("zip"))
            }
        }
        field("countryCode") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("countryCode"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get<String>("countryCode"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("countryCode").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("countryCode").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("countryCode"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("countryCode"))
            }
        }
        field("vat") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<String>("vat"), value)
            }
            contains { criteriaBuilder, _, root, value ->
                criteriaBuilder.like(root.get("vat"), "%$value%")
            }
            any { criteriaBuilder, _, root, value ->
                root.get<String>("vat").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<String>("vat").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("vat"))
            }
            isEmpty { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<String>("vat"))
            }
        }
        field("active") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<Boolean>("active"), (value as String).toBoolean())
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        }
        field("clientTypeId") {
            eq { criteriaBuilder, _, root, value ->
                criteriaBuilder.equal(root.get<UUID>("type.id"), value)
            }
            any{ criteriaBuilder, _, root, value ->
                root.get<UUID>("type").`in`(value)
            }
            none { criteriaBuilder, _, root, value ->
                criteriaBuilder.not(root.get<UUID>("type").`in`(value))
            }
            isNull { criteriaBuilder, _, root, value ->
                criteriaBuilder.isNull(root.get<UUID>("type"))
            }
        }
    }
}
