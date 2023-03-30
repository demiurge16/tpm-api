package net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities

import jakarta.persistence.*
import net.nuclearprometheus.tpm.applicationserver.adapters.database.project.entities.ProjectDatabaseModel
import java.util.*

@Entity(name = "Unit")
@Table(name = "unit")
open class UnitDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, length = 128) open var name: String,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false) open var active: Boolean,
    @OneToMany(mappedBy = "unit") open var project: MutableList<ProjectDatabaseModel>
)