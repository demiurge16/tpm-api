package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

object ProjectMapper {

    fun Project.toView() = ProjectResponse.Project(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = Language(
            code = sourceLanguage.id.value,
            name = sourceLanguage.name,
        ),
        targetLanguages = targetLanguages.map {
            Language(
                code = it.id.value,
                name = it.name
            )
        },
        accuracy = Accuracy(
            id = accuracy.id.value,
            name = accuracy.name,
            description = accuracy.description
        ),
        industry = Industry(
            id = industry.id.value,
            name = industry.name,
            description = industry.description
        ),
        unit = Unit(
            id = unit.id.value,
            name = unit.name,
            description = unit.description
        ),
        serviceTypes = serviceTypes.map {
            ServiceType(
                id = it.id.value,
                name = it.name,
                description = it.description
            )
        },
        amount = amount,
        expectedStart = expectedStart,
        internalDeadline = internalDeadline,
        externalDeadline = externalDeadline,
        budget = budget,
        currency = Currency(
            code = currency.id.value,
            name = currency.name
        ),
        status = Status(
            status = status,
            title = status.title,
            description = status.description
        ),
        client = client.toView(),
    )

    fun Project.toStartMovedResponse() = ProjectResponse.StartMoved(
        id = id.value,
        expectedStart = expectedStart
    )

    fun Project.toDeadlineMovedResponse() = ProjectResponse.DeadlineMoved(
        id = id.value,
        internalDeadline = internalDeadline,
        externalDeadline = externalDeadline
    )
}
