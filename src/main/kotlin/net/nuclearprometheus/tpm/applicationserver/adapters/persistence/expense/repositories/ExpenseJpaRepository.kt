package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExpenseJpaRepository : JpaRepository<ExpenseDatabaseModel, UUID> {
    fun findAllByProjectId(projectId: UUID): List<ExpenseDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
