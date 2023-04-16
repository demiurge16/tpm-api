package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectTaskResponse {
    data class View(
        val id: UUID,
        val title: String,
        val description: String,
        val sourceLanguage: LanguageView,
        val targetLanguage: LanguageView,
        val accuracy: AccuracyView,
        val industry: IndustryView,
        val unit: UnitView,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val deadline: ZonedDateTime,
        val budget: BigDecimal,
        val currency: CurrencyView,
        val priority: PriorityView,
        val projectId: UUID
    ) : ProjectTaskResponse() {

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

        data class PriorityView(
            val id: UUID,
            val name: String,
            val description: String,
            val emoji: String,
            val value: Int
        )
    }
}