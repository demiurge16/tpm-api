package net.nuclearprometheus.tpm.applicationserver.domain.model.file.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.UUID

object FileSpecificationBuilder : SpecificationBuilder<File>() {
    val id = uniqueValue("id", UUID::class)
    val name = string("name")
    val size = comparable("size", Long::class)
    val type = uniqueValue("type", String::class)
    val uploadTime = comparable("uploadTime", String::class)
    val uploaderId = uniqueValue("uploaderId", UUID::class)
    val projectId = uniqueValue("projectId", UUID::class)
    val location = string("location")
}