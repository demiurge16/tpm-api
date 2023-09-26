package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

@Entity(name = "TimeEntry")
@Table(name = "time_entry")
open class TimeEntryDatabaseModel(
    @Id() open var id: UUID,
    @Column(nullable = false, columnDefinition = "uuid") open var userId: UUID,
    @Column(nullable = false) open var date: LocalDate,
    @Column(nullable = false) open var timeSpent: Int,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var timeUnit: TimeUnitDatabaseModel,
    @Column(nullable = false) @Enumerated(EnumType.STRING) open var status: TimeEntryStatusDatabaseModel,
    @Column(nullable = false, length = 512) open var description: String,
    @Column(nullable = false, columnDefinition = "uuid") open var taskId: UUID
)
