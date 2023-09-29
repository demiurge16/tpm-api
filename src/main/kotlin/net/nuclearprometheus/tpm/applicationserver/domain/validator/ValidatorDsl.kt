package net.nuclearprometheus.tpm.applicationserver.domain.validator

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationException

fun validate(block: Validator.() -> Unit) {
    val validator = Validator()
    validator.block()

    val errors = validator.validate()
    if (errors.isNotEmpty()) {
        throw ValidationException(errors)
    }
}
