package net.nuclearprometheus.tpm.applicationserver.adapters.database.expense.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.expense.repositories.ExpenseJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import org.springframework.stereotype.Repository

@Repository
class ExpenseRepositoryImpl(
    private val jpaRepository: ExpenseJpaRepository,
    private val teamMemberRepository: TeamMemberRepository
) : ExpenseRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: ExpenseId): Expense? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<ExpenseId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: Expense) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain()
    override fun createAll(entities: List<Expense>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain() }
    override fun update(entity: Expense) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain()
    override fun updateAll(entities: List<Expense>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain() }
    override fun delete(id: ExpenseId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ExpenseId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain() }

    companion object Mappers {

        fun ExpenseDatabaseModel.toDomain() = Expense(
            id = ExpenseId(id),
            amount = amount,
            date = date,
            description = description,
            projectId = ProjectId(projectId),
            teamMemberId = TeamMemberId(teamMember.id)
        )

        fun Expense.toDatabaseModel(teamMemberRepository: TeamMemberRepository) = ExpenseDatabaseModel(
            id = id.value,
            amount = amount,
            date = date,
            description = description,
            projectId = projectId.value,
            teamMember = teamMemberRepository.get(teamMemberId)?.toDatabaseModel()
                ?: throw IllegalArgumentException("Team member with id $teamMemberId does not exist")
        )
    }
}