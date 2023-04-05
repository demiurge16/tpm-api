package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities

import jakarta.persistence.*
import java.util.*

@Entity(name = "Industry")
@Table(name = "industry")
open class IndustryDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var name: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var active: Boolean
)