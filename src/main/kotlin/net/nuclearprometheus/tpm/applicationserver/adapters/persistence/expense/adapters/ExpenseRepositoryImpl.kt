package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ExpenseCategoryRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.adapters.ExpenseCategoryRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.repositories.ExpenseJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.UnknownCurrency
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import org.springframework.stereotype.Repository

@Repository
class ExpenseRepositoryImpl(
    private val jpaRepository: ExpenseJpaRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val currencyRepository: CurrencyRepository
) : ExpenseRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(currencyRepository) }
    override fun get(id: ExpenseId): Expense? = jpaRepository.findById(id.value).map { it.toDomain(currencyRepository) }.orElse(null)
    override fun get(ids: List<ExpenseId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(currencyRepository) }
    override fun create(entity: Expense) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain(currencyRepository)
    override fun createAll(entities: List<Expense>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain(currencyRepository) }
    override fun update(entity: Expense) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain(currencyRepository)
    override fun updateAll(entities: List<Expense>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain(currencyRepository) }
    override fun delete(id: ExpenseId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ExpenseId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain(currencyRepository) }
    override fun deleteAllByProjectId(projectId: ProjectId) = jpaRepository.deleteAllByProjectId(projectId.value)

    companion object Mappers {

        fun ExpenseDatabaseModel.toDomain(currencyRepository: CurrencyRepository) = Expense(
            id = ExpenseId(id),
            amount = amount,
            currency = CurrencyCode(currency)
                .let { currencyRepository.get(it) ?: UnknownCurrency(it) },
            date = date,
            description = description,
            projectId = ProjectId(projectId),
            teamMemberId = TeamMemberId(teamMember.id),
            category = category.toDomain()
        )

        fun Expense.toDatabaseModel(teamMemberRepository: TeamMemberRepository) = ExpenseDatabaseModel(
            id = id.value,
            amount = amount,
            currency = currency.id.value,
            date = date,
            description = description,
            projectId = projectId.value,
            teamMember = teamMemberRepository.get(teamMemberId)?.toDatabaseModel()
                ?: throw IllegalArgumentException("Team member with id $teamMemberId does not exist"),
            category = category.toDatabaseModel()
        )
    }
}