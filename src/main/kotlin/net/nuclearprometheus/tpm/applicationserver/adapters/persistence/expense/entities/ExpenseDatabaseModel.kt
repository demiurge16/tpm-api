package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Expense")
@Table(name = "expense")
open class ExpenseDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var amount: Double,
    @Column(nullable = false) open var date: ZonedDateTime,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var teamMember: TeamMemberDatabaseModel,
    @Column(nullable = false) open var projectId: UUID
)
