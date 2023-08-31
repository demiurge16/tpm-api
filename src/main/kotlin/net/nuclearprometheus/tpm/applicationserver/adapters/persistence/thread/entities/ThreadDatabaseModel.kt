package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities

import jakarta.persistence.*
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Thread")
@Table(name = "thread")
open class ThreadDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 512) open var title: String,
    @Column(nullable = false, columnDefinition = "text") open var content: String,
    @Column(nullable = false) open var createdAt: ZonedDateTime,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var status: ThreadStatusDatabaseModel,
    @Column(nullable = false, columnDefinition = "uuid") open var authorId: UUID,
    @Column(nullable = false, columnDefinition = "uuid") open var projectId: UUID,
)