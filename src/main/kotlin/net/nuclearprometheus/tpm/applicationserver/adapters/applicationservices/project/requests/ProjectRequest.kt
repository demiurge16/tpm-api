package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Project>( page, size, sort, search) {

        override fun toString(): String {
            return "ProjectRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }

    data class Create(
        val title: String,
        val description: String,
        val sourceLanguage: String,
        val targetLanguages: kotlin.collections.List<String>,
        val accuracyId: UUID,
        val industryId: UUID,
        val unitId: UUID,
        val serviceTypeIds: kotlin.collections.List<UUID>,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val internalDeadline: ZonedDateTime,
        val externalDeadline: ZonedDateTime,
        val budget: BigDecimal,
        val currencyCode: String,
        val clientId: UUID
    ) : ProjectRequest()

    data class Update(
        val title: String,
        val description: String,
        val sourceLanguage: String,
        val targetLanguages: kotlin.collections.List<String>,
        val accuracyId: UUID,
        val industryId: UUID,
        val unitId: UUID,
        val serviceTypeIds: kotlin.collections.List<UUID>,
        val amount: Int,
        val budget: BigDecimal,
        val currencyCode: String,
        val clientId: UUID
    ) : ProjectRequest()

    data class MoveStart(val expectedStart: ZonedDateTime) : ProjectRequest()

    data class MoveDeadline(val internalDeadline: ZonedDateTime, val externalDeadline: ZonedDateTime) : ProjectRequest()
}