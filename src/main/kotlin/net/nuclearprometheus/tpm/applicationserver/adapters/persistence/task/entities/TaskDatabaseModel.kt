package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.*
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Task")
@Table(name = "task")
open class TaskDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var title: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false, length = 16) open var sourceLanguage: String,
    @Column(nullable = false, length = 16) open var targetLanguage: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var accuracy: AccuracyDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var industry: IndustryDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var unit: UnitDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var serviceType: ServiceTypeDatabaseModel,
    @Column(nullable = false) open var amount: Int,
    @Column(nullable = false) open var expectedStart: ZonedDateTime,
    @Column(nullable = false) open var deadline: ZonedDateTime,
    @Column(nullable = false) open var budget: BigDecimal,
    @Column(nullable = false) open var currency: String,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var status: TaskStatusDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var priority: PriorityDatabaseModel,
    @Column(nullable = false, columnDefinition = "uuid") open var assigneeId: UUID?,
    @Column(nullable = false, columnDefinition = "uuid") open var projectId: UUID
)
