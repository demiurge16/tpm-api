package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.client.entities

import jakarta.persistence.*
import java.util.*

@Entity(name = "Client")
@Table(name = "client")
open class ClientDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 255) open var name: String,
    @Column(nullable = false, length = 255) open var email: String,
    @Column(nullable = false, length = 64) open var phone: String,
    @Column(nullable = false, length = 512) open var address: String,
    @Column(nullable = false, length = 255) open var city: String,
    @Column(nullable = false, length = 255) open var state: String,
    @Column(nullable = false, length = 32) open var zip: String,
    @Column(nullable = false, length = 16) open var countryCode: String,
    @Column(nullable = false, length = 64) open var vat: String,
    @Column(nullable = false, length = 512) open var notes: String,
    @Column(nullable = false) open var active: Boolean,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST]) @JoinColumn(nullable = false) open var type: ClientTypeDatabaseModel
)
