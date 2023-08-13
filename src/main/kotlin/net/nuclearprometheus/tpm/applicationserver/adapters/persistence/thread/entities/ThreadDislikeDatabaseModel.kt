package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "ThreadDislike")
@Table(name = "thread_dislike")
open class ThreadDislikeDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false) open var createdAt: ZonedDateTime,
    @Column(nullable = false, columnDefinition = "uuid") open var authorId: UUID,
    @Column(nullable = false, columnDefinition = "uuid") open var threadId: UUID,
)