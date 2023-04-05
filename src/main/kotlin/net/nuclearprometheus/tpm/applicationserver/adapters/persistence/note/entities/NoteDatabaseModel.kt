package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.note.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Note")
@Table(name = "note")
open class NoteDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 512) open var content: String,
    @Column(nullable = false) open var createdAt: ZonedDateTime,
    @ManyToOne @JoinColumn(nullable = false) open var author: TeamMemberDatabaseModel,
    @Column(nullable = false) open var projectId: UUID
)
