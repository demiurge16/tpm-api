package net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.time.ZonedDateTime
import java.util.*

object TaskSpecificationBuilder : SpecificationBuilder<Task>() {
    val id = uniqueValue("id", UUID::class)
    val title = string("title")
    val sourceLanguage = uniqueValue("sourceLanguage", String::class)
    val targetLanguage = uniqueValue("targetLanguage", String::class)
    val accuracyId = uniqueValue("accuracyId", UUID::class)
    val industryId = uniqueValue("industryId", UUID::class)
    val unitId = uniqueValue("unitId", UUID::class)
    val amount = comparable("amount", Int::class)
    val expectedStart = comparable("expectedStart", ZonedDateTime::class)
    val deadline = comparable("deadline", ZonedDateTime::class)
    val budget = comparable("budget", Int::class)
    val currency = uniqueValue("currency", String::class)
    val status = enum("status", TaskStatus::class)
    val priorityId = uniqueValue("priorityId", UUID::class)
    val assigneeId = uniqueValue("assigneeId", UUID::class)
    val projectId = uniqueValue("projectId", UUID::class)
}

