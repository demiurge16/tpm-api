package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import java.math.BigDecimal
import java.util.*

data class UpdateTask(
    val title: String,
    val description: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val accuracy: UUID,
    val industry: UUID,
    val unit: UUID,
    val serviceType: UUID,
    val amount: Int,
    val budget: BigDecimal,
    val currency: String,
)