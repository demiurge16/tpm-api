package net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common

class ValidationException(val errors: List<ValidationError>) : Exception()
