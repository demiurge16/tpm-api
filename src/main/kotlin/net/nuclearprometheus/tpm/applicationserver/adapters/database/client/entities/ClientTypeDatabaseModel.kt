package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities

import java.util.UUID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ClientType")
@Table(name = "client_type")
open class ClientTypeDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 255) open var name: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var corporate: Boolean,
    @Column(nullable = false) open var active: Boolean
)
