package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectResponse {

    data class View(
        val id: UUID,
        val title: String,
        val description: String,
        val sourceLanguage: LanguageView,
        val targetLanguages: List<LanguageView>,
        val accuracy: AccuracyView,
        val industry: IndustryView,
        val unit: UnitView,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val internalDeadline: ZonedDateTime,
        val externalDeadline: ZonedDateTime,
        val budget: BigDecimal,
        val currency: CurrencyView,
        val status: ProjectStatusView,
        val client: ClientView
    ) : ProjectResponse() {

        data class LanguageView(
            val code: String,
            val name: String
        )

        data class AccuracyView(
            val id: UUID,
            val name: String,
            val description: String,
        )

        data class IndustryView(
            val id: UUID,
            val name: String,
            val description: String,
        )

        data class UnitView(
            val id: UUID,
            val name: String,
            val description: String,
        )

        data class CurrencyView(
            val code: String,
            val name: String
        )

        data class ProjectStatusView(
            val status: ProjectStatus,
            val name: String,
            val description: String,
        )

        data class ClientView(
            val id: UUID,
            val name: String,
            val email: String,
            val phone: String,
            val address: String,
            val city: String,
            val state: String,
            val zip: String,
            val country: CountryView,
            val vat: String,
            val notes: String,
            val type: ClientTypeView,
        ) {

            data class CountryView(
                val code: String,
                val name: String,
                val nativeNames: List<String>,
                val currencies: List<CurrencyView>,
                val languages: List<LanguageView>,
                val emoji: String
            ) {
                data class LanguageView(
                    val code: String,
                    val name: String
                )

                data class CurrencyView(
                    val code: String,
                    val name: String
                )
            }
        }

        data class ClientTypeView(
            val id: UUID,
            val name: String,
            val description: String,
            val corporate: Boolean
        )
    }

    data class StartMoved(
        val id: UUID,
        val expectedStart: ZonedDateTime
    ) : ProjectResponse()

    data class DeadlineMoved(
        val id: UUID,
        val internalDeadline: ZonedDateTime,
        val externalDeadline: ZonedDateTime
    ) : ProjectResponse()
}
