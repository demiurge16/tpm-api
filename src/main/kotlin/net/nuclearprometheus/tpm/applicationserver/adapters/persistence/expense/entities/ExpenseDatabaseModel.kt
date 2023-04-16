package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ExpenseCategoryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.PriorityDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Expense")
@Table(name = "expense")
open class ExpenseDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var amount: BigDecimal,
    @Column(nullable = false, length = 16) open var currency: String,
    @Column(nullable = false) open var date: ZonedDateTime,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var teamMember: TeamMemberDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var category: ExpenseCategoryDatabaseModel,
    @Column(nullable = false) open var projectId: UUID
)
