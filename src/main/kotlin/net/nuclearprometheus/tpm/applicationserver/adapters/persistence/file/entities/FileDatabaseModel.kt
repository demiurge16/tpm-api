package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "File")
@Table(name = "file")
open class FileDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var name: String,
    @Column(nullable = false, length = 128) open var type: String,
    @Column(nullable = false, length = 256) open var location: String,
    @Column(nullable = false) open var size: Long,
    @Column(nullable = false) open var uploadTime: ZonedDateTime,
    @Column(nullable = false, columnDefinition = "uuid") open var uploaderId: UUID,
    @Column(nullable = false, columnDefinition = "uuid") open var projectId: UUID
)
