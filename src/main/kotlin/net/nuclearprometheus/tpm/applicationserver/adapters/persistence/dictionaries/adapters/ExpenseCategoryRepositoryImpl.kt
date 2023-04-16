package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ExpenseCategoryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories.ExpenseCategoryJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import org.springframework.stereotype.Repository

@Repository
class ExpenseCategoryRepositoryImpl(
    private val jpaRepository: ExpenseCategoryJpaRepository
) : ExpenseCategoryRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: ExpenseCategoryId) = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<ExpenseCategoryId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: ExpenseCategory) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<ExpenseCategory>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: ExpenseCategory) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<ExpenseCategory>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: ExpenseCategoryId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ExpenseCategoryId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun ExpenseCategoryDatabaseModel.toDomain() = ExpenseCategory(
            id = ExpenseCategoryId(id),
            name = name,
            description = description,
            active = active
        )

        fun ExpenseCategory.toDatabaseModel() = ExpenseCategoryDatabaseModel(
            id = id.value,
            name = name,
            description = description,
            active = active
        )
    }
}