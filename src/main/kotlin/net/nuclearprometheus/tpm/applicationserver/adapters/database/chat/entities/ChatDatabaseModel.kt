package net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.database.project.entities.ProjectDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.entities.TeamMemberDatabaseModel
import java.util.*

@Entity(name = "Chat")
@Table(name = "chat")
open class ChatDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var title: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var status: ChatStatusDatabaseModel,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var owner: TeamMemberDatabaseModel,
    @Column(nullable = false) open var projectId: UUID,
    @ManyToMany(fetch = FetchType.LAZY) @JoinTable(
        name = "chat_participant",
        joinColumns = [JoinColumn(name = "chat_id")],
        inverseJoinColumns = [JoinColumn(name = "team_member_id")]
    )
    open var participants: MutableList<TeamMemberDatabaseModel>
)
