package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities

import java.util.UUID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "client_type")
open class ClientTypeDatabaseModel(
    @get:Id() open var id: UUID,
    @get:Column(nullable = false, length = 255) open var name: String,
    @get:Column(nullable = false, length = 512) open var description: String,
    @get:Column(nullable = false) open var corporate: Boolean,
    @get:Column(nullable = false) open var active: Boolean
)
