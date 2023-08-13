package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ExpenseCategoryRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ExpenseCategoryRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.repositories.ExpenseJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownCurrency
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class ExpenseRepositoryImpl(
    private val jpaRepository: ExpenseJpaRepository,
    private val userRepository: UserRepository,
    private val currencyRepository: CurrencyRepository
) : ExpenseRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(currencyRepository, userRepository) }
    override fun get(id: ExpenseId): Expense? = jpaRepository.findById(id.value).map { it.toDomain(currencyRepository, userRepository) }.orElse(null)
    override fun get(ids: List<ExpenseId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(currencyRepository, userRepository) }
    override fun create(entity: Expense) = jpaRepository.save(entity.toDatabaseModel()).toDomain(currencyRepository, userRepository)
    override fun createAll(entities: List<Expense>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(currencyRepository, userRepository) }
    override fun update(entity: Expense) = jpaRepository.save(entity.toDatabaseModel()).toDomain(currencyRepository, userRepository)
    override fun updateAll(entities: List<Expense>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(currencyRepository, userRepository) }
    override fun delete(id: ExpenseId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ExpenseId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain(currencyRepository, userRepository) }
    override fun deleteAllByProjectId(projectId: ProjectId) = jpaRepository.deleteAllByProjectId(projectId.value)

    companion object Mappers {

        fun ExpenseDatabaseModel.toDomain(currencyRepository: CurrencyRepository, userRepository: UserRepository) =
            Expense(
                id = ExpenseId(id),
                amount = amount,
                currency = CurrencyCode(currency)
                    .let { currencyRepository.get(it) ?: UnknownCurrency(it) },
                date = date,
                description = description,
                projectId = ProjectId(projectId),
                spender = spenderId.let { userRepository.get(UserId(it)) ?: throw IllegalArgumentException("Team member with id $it does not exist") },
                category = category.toDomain()
            )

        fun Expense.toDatabaseModel() = ExpenseDatabaseModel(
            id = id.value,
            amount = amount,
            currency = currency.id.value,
            date = date,
            description = description,
            projectId = projectId.value,
            spenderId = spender.id.value,
            category = category.toDatabaseModel()
        )
    }
}