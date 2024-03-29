package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientId
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import java.math.BigDecimal
import java.time.ZonedDateTime

interface ProjectService {

    fun create(
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguages: List<LanguageCode>,
        accuracyId: AccuracyId,
        industryId: IndustryId,
        unitId: UnitId,
        serviceTypeIds: List<ServiceTypeId>,
        amount: Int,
        expectedStart: ZonedDateTime,
        internalDeadline: ZonedDateTime,
        externalDeadline: ZonedDateTime,
        budget: BigDecimal,
        currencyCode: CurrencyCode,
        clientId: ClientId
    ): Project

    fun update(
        id: ProjectId,
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguages: List<LanguageCode>,
        accuracyId: AccuracyId,
        industryId: IndustryId,
        unitId: UnitId,
        serviceTypeIds: List<ServiceTypeId>,
        amount: Int,
        budget: BigDecimal,
        currencyCode: CurrencyCode,
        clientId: ClientId
    ): Project

    fun moveStart(id: ProjectId, expectedStart: ZonedDateTime): Project
    fun moveDeadlines(id: ProjectId, internalDeadline: ZonedDateTime, externalDeadline: ZonedDateTime): Project
    fun finishDraft(id: ProjectId): Project
    fun backToDraft(id: ProjectId): Project
    fun startProgress(id: ProjectId): Project
    fun startReview(id: ProjectId): Project
    fun approve(id: ProjectId): Project
    fun reject(id: ProjectId): Project
    fun deliver(id: ProjectId): Project
    fun invoice(id: ProjectId): Project
    fun pay(id: ProjectId): Project
    fun putOnHold(id: ProjectId): Project
    fun resume(id: ProjectId): Project
    fun cancel(id: ProjectId): Project
    fun reopen(id: ProjectId): Project
}
