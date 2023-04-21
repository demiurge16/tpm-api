package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.LanguageView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.AccuracyView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.IndustryView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.UnitView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.CurrencyView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.ProjectStatusView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.ClientView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.ClientView.CountryView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectResponse.View.ClientTypeView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project

object ProjectMapper {

    fun Project.toView() = ProjectResponse.View(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = LanguageView(
            code = sourceLanguage.id.value,
            name = sourceLanguage.name,
        ),
        targetLanguages = targetLanguages.map {
            LanguageView(
                code = it.id.value,
                name = it.name
            )
        },
        accuracy = AccuracyView(
            id = accuracy.id.value,
            name = accuracy.name,
            description = accuracy.description
        ),
        industry = IndustryView(
            id = industry.id.value,
            name = industry.name,
            description = industry.description
        ),
        unit = UnitView(
            id = unit.id.value,
            name = unit.name,
            description = unit.description
        ),
        amount = amount,
        expectedStart = expectedStart,
        internalDeadline = internalDeadline,
        externalDeadline = externalDeadline,
        budget = budget,
        currency = CurrencyView(
            code = currency.id.value,
            name = currency.name
        ),
        status = ProjectStatusView(
            status = status,
            name = status.name,
            description = status.shortDescription
        ),
        client = ClientView(
            id = client.id.value,
            name = client.name,
            email = client.email,
            phone = client.phone,
            address = client.address,
            city = client.city,
            state = client.state,
            country = CountryView(
                code = client.country.id.value,
                name = client.country.name,
                nativeNames = client.country.nativeNames,
                currencies = client.country.currencies.map {
                    CountryView.CurrencyView(
                        code = it.key.value,
                        name = it.value,
                    )
                },
                languages = client.country.languages.map {
                    CountryView.LanguageView(
                        code = it.key.value,
                        name = it.value,
                    )
                },
                emoji = client.country.emoji,
            ),
            zip = client.zip,
            vat = client.vat,
            notes = client.notes,
            type = ClientTypeView(
                id = client.type.id.value,
                name = client.type.name,
                description = client.type.description,
                corporate = client.type.corporate
            )
        ),
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
