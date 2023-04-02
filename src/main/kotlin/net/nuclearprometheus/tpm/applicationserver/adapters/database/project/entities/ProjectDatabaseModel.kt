package net.nuclearprometheus.tpm.applicationserver.adapters.database.project.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities.ChatDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.AccuracyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.IndustryDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.UnitDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.expense.entities.ExpenseDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.note.entites.NoteDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.task.entities.TaskDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.entities.TeamMemberDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Project")
@Table(name = "project")
open class ProjectDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var title: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false, length = 16) open var sourceLanguage: String,
    @ElementCollection @CollectionTable(name = "project_target_language") @Column(nullable = false, length = 16, name = "target_language") open var targetLanguages: List<String>,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var accuracy: AccuracyDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var industry: IndustryDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var unit: UnitDatabaseModel,
    @Column(nullable = false) open var amount: Int,
    @Column(nullable = false) open var expectedStart: ZonedDateTime,
    @Column(nullable = false) open var internalDeadline: ZonedDateTime,
    @Column(nullable = false) open var externalDeadline: ZonedDateTime,
    @Column(nullable = false) open var budget: BigDecimal,
    @Column(nullable = false, length = 16) open var currency: String,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var status: ProjectStatus,
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) open var teamMembers: MutableList<TeamMemberDatabaseModel>,
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) open var tasks: MutableList<TaskDatabaseModel>,
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) open var expenses: MutableList<ExpenseDatabaseModel>,
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) open var notes: MutableList<NoteDatabaseModel>,
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) open var files: MutableList<FileDatabaseModel>,
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) open var chats: MutableList<ChatDatabaseModel>,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var client: ClientDatabaseModel
)
