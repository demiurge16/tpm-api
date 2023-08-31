package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "Reply")
@Table(name = "reply")
open class ReplyDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, columnDefinition = "text") open var content: String,
    @Column(nullable = false) open var createdAt: ZonedDateTime,
    @Column(nullable = false) open var deleted: Boolean,
    @Column(nullable = false, columnDefinition = "uuid") open var authorId: UUID,
    @Column(nullable = true, columnDefinition = "uuid") open var parentReplyId: UUID?,
    @Column(nullable = false, columnDefinition = "uuid") open var threadId: UUID,
)
