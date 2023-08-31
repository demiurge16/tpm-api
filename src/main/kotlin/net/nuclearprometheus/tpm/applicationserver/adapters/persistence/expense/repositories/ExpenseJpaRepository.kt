package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities.ExpenseDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface ExpenseJpaRepository : JpaRepository<ExpenseDatabaseModel, UUID>, JpaSpecificationExecutor<ExpenseDatabaseModel> {
    fun findAllByProjectId(projectId: UUID): List<ExpenseDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
}
