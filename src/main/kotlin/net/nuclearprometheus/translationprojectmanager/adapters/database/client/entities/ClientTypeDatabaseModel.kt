package net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "client_type")
open class ClientTypeDatabaseModel(
    @get:Id() open var id: UUID,
    @get:Column(nullable = false, length = 255) open var name: String,
    @get:Column(nullable = false, length = 512) open var description: String,
    @get:Column(nullable = false) open var corporate: Boolean,
    @get:Column(nullable = false) open var active: Boolean
)
