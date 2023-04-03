package net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.entities.TeamMemberDatabaseModel
import java.time.ZonedDateTime
import java.util.UUID

@Entity(name = "Message")
@Table(name = "message")
open class MessageDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 512) open var message: String,
    @Column(nullable = false) open var timestamp: ZonedDateTime,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var author: TeamMemberDatabaseModel,
    @Column(nullable = false) open var chatId: UUID
)
