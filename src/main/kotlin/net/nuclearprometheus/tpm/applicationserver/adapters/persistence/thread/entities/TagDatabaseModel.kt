package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity(name = "Tag")
@Table(name = "tag")
open class TagDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false) open var name: String,
    @Column(nullable = false, columnDefinition = "uuid") open var threadId: UUID
)