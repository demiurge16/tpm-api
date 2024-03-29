package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities

import jakarta.persistence.*
import java.util.*

@Entity(name = "TeamMember")
@Table(name = "team_member_role")
open class TeamMemberRoleDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var role: ProjectRoleDatabaseModel,
    @Column(nullable = false) open var userId: UUID,
    @Column(nullable = false) open var projectId: UUID
)
