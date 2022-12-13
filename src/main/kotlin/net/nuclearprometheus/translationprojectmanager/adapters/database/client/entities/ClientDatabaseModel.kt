package net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "client")
open class ClientDatabaseModel(
    @get:Id() open var id: UUID,
    @get:Column(nullable = false, length = 255) open var name: String,
    @get:Column(nullable = false, length = 255) open var email: String,
    @get:Column(nullable = false, length = 64) open var phone: String,
    @get:Column(nullable = false, length = 512) open var address: String,
    @get:Column(nullable = false, length = 255) open var city: String,
    @get:Column(nullable = false, length = 255) open var state: String,
    @get:Column(nullable = false, length = 32) open var zip: String,
    @get:Column(nullable = false, length = 16) open var countryCode: String,
    @get:Column(nullable = false, length = 64) open var vat: String,
    @get:Column(nullable = false, length = 512) open var notes: String,
    @get:Column(nullable = false) open var active: Boolean,
    @get:ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false) open var type: ClientTypeDatabaseModel
)
