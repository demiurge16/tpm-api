package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities

import jakarta.persistence.*
import java.util.*

@Entity(name = "Unit")
@Table(name = "unit")
open class UnitDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var name: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var volume: Int,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var measurement: MeasurementDatabaseModel,
    @Column(nullable = false) open var active: Boolean
)
