package net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeUnit
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.time.LocalDate
import java.util.*

object TimeEntrySpecificationBuilder : SpecificationBuilder<TimeEntry>() {
    val id = uniqueValue("id", UUID::class)
    val userId = uniqueValue("userId", UUID::class)
    val date = comparable("date", LocalDate::class)
    val timeSpent = comparable("timeSpent", Int::class)
    val timeUnit = enum("timeUnit", TimeUnit::class)
    val status = enum("status", TimeEntryStatus::class)
    val taskId = uniqueValue("taskId", UUID::class)
}