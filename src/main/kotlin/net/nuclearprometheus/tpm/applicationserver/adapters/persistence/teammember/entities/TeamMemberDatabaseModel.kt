package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities

import jakarta.persistence.*
import java.util.*

@Entity(name = "TeamMember")
@Table(name = "team_member")
open class TeamMemberDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var role: TeamMemberRoleDatabaseModel,
    @Column(nullable = false) open var userId: UUID,
    @Column(nullable = false) open var projectId: UUID
)
