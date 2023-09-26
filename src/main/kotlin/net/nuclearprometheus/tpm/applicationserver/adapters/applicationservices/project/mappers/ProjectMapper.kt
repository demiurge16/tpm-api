package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.mappers.ClientMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.AccuracyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.CurrencyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.IndustryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.LanguageMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ServiceTypeMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.UnitMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStartMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Project as ProjectResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

object ProjectMapper {

    fun Project.toView() = ProjectResponse(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = sourceLanguage.toView(),
        targetLanguages = targetLanguages.map { it.toView() },
        accuracy = accuracy.toView(),
        industry = industry.toView(),
        unit = unit.toView(),
        serviceTypes = serviceTypes.map { it.toView() },
        amount = amount,
        expectedStart = expectedStart,
        internalDeadline = internalDeadline,
        externalDeadline = externalDeadline,
        budget = budget,
        currency = currency.toView(),
        status = ProjectStatus(
            status = status,
            title = status.title,
            description = status.description
        ),
        client = client.toView(),
    )

    fun Project.toShortView() = ProjectShortView(
        id = id.value,
        title = title,
        status = ProjectStatus(
            status = status,
            title = status.title,
            description = status.description
        ),
    )

    fun Project.toStartMovedResponse() = ProjectStartMoved(
        id = id.value,
        expectedStart = expectedStart
    )

    fun Project.toDeadlineMovedResponse() = ProjectDeadlineMoved(
        id = id.value,
        internalDeadline = internalDeadline,
        externalDeadline = externalDeadline
    )
}
