package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.specifications

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Root
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.SpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ServiceTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ServiceType
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceTypeSpecificationBuilder : SpecificationBuilder<ServiceType, ServiceTypeDatabaseModel>() {

    override val filters = mapOf(
        "id" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<UUID>("id"), value)
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                root.get<UUID>("id").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<UUID>("id").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<UUID>("id"))
            }
        ),
        "name" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<String>("name"), value)
            },
            "contains" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                criteriaBuilder.like(root.get<String>("name"), "%$value%")
            },
            "any" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                root.get<String>("name").`in`(value)
            },
            "none" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                criteriaBuilder.not(root.get<String>("name").`in`(value))
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            },
            "empty" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<String>("name"))
            }
        ),
        "active" to mapOf(
            "eq" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, value: Any ->
                criteriaBuilder.equal(root.get<Boolean>("active"), value)
            },
            "null" to { criteriaBuilder: CriteriaBuilder, root: Root<ServiceTypeDatabaseModel>, _: Any ->
                criteriaBuilder.isNull(root.get<Boolean>("active"))
            }
        )
    )
}