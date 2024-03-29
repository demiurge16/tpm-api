package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity(name = "ExpenseCategory")
@Table(name = "expense_category")
open class ExpenseCategoryDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var name: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var active: Boolean
)
