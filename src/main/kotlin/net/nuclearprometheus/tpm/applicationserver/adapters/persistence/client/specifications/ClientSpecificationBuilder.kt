package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.Client
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operation
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClientSpecificationBuilder : SpecificationBuilder<Client, ClientDatabaseModel>() {

    override val filters = mapOf(
        "id" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<UUID>("id").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        ),
        "name" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("name").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        ),
        "email" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("email"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("email"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("email").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("email").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("email"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("email"))
            }
        ),
        "phone" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("phone"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("phone"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("phone").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("phone").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("phone"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("phone"))
            }
        ),
        "address" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("address"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("address"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("address").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("address").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("address"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("address"))
            }
        ),
        "city" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("city"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("city"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("city").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("city").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("city"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("city"))
            }
        ),
        "state" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("state"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("state"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("state").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("state").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("state"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("state"))
            }
        ),
        "zip" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("zip"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("zip"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("zip").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("zip").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("zip"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("zip"))
            }
        ),
        "countryCode" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("countryCode"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("countryCode"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("countryCode").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("countryCode").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("countryCode"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("countryCode"))
            }
        ),
        "vat" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("vat"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("vat"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<String>("vat").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("vat").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("vat"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("vat"))
            }
        ),
        "active" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Boolean>("active"), value)
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        ),
        "clientTypeId" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<UUID>("type.id"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                root.get<UUID>("type").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<UUID>("type").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ClientDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<UUID>("type"))
            }
        ),
    )
}
