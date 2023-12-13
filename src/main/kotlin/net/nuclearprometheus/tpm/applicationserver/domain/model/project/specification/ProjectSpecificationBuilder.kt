package net.nuclearprometheus.tpm.applicationserver.domain.model.project.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

object ProjectSpecificationBuilder : SpecificationBuilder<Project>() {
    val id = uniqueValue("id", UUID::class)
    val title = string("title")
    val sourceLanguage = uniqueValue("sourceLanguage", String::class)
    val targetLanguages = collection("targetLanguages", String::class)
    val accuracyId = uniqueValue("accuracyId", UUID::class)
    val industryId = uniqueValue("industryId", UUID::class)
    val unitId = uniqueValue("unitId", UUID::class)
    val amount = comparable("amount", Int::class)
    val expectedStart = comparable("expectedStart", ZonedDateTime::class)
    val internalDeadline = comparable("internalDeadline", ZonedDateTime::class)
    val externalDeadline = comparable("externalDeadline", ZonedDateTime::class)
    val budget = comparable("budget", BigDecimal::class)
    val currency = uniqueValue("currency", String::class)
    val status = enum("status", ProjectStatus::class)
    val clientId = uniqueValue("clientId", UUID::class)
}
