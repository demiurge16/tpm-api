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
    @Column(nullable = false) open var projectId: UUID
)
