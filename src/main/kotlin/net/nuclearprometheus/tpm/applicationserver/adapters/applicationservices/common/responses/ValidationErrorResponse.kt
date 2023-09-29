package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError

data class ValidationErrorResponse(val message: String, val errors: List<ValidationError>)