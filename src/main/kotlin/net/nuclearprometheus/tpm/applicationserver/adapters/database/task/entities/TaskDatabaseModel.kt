package net.nuclearprometheus.tpm.applicationserver.adapters.database.task.entities

import TaskStatusDatabaseModel
import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.entities.TeamMemberDatabaseModel
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
    @Column(nullable = false) open var amount: Int,
    @Column(nullable = false) open var expectedStart: ZonedDateTime,
    @Column(nullable = false) open var deadline: ZonedDateTime,
    @Column(nullable = false) open var budget: BigDecimal,
    @Column(nullable = false) open var currency: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = true) open var assignee: TeamMemberDatabaseModel?,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var status: TaskStatusDatabaseModel,
    @ManyToOne @JoinColumn(nullable = false) open var project: ProjectDatabaseModel
)
