package net.nuclearprometheus.tpm.applicationserver.domain.model.project.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.OrderByBuilder

object ProjectOrderByBuilder : OrderByBuilder<Project>() {
    val title = sort("title")
    val expectedStart = sort("expectedStart")
    val internalDeadline = sort("internalDeadline")
    val externalDeadline = sort("externalDeadline")
    val status = sort("status")

    val byDeadline = externalDeadline.ascending and internalDeadline.ascending
}