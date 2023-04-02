package net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities.ChatDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.task.entities.TaskDatabaseModel
import java.util.*

@Entity(name = "TeamMember")
@Table(name = "team_member")
open class TeamMemberDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var role: TeamMemberRoleDatabaseModel,
    @Column(nullable = false) open var user: UUID,
    @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY) open var tasks: MutableList<TaskDatabaseModel>,
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY) open var ownedChats: MutableList<ChatDatabaseModel>,
    @ManyToMany(fetch = FetchType.LAZY) @JoinTable(
        name = "chat_participant",
        joinColumns = [JoinColumn(name = "team_member_id")],
        inverseJoinColumns = [JoinColumn(name = "chat_id")]
    ) open var chat: MutableList<ChatDatabaseModel>,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var project: ProjectDatabaseModel
)
