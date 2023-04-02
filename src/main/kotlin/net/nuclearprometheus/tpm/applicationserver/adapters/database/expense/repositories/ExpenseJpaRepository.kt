package net.nuclearprometheus.tpm.applicationserver.adapters.database.expense.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.expense.entities.ExpenseDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExpenseJpaRepository : JpaRepository<ExpenseDatabaseModel, UUID>
