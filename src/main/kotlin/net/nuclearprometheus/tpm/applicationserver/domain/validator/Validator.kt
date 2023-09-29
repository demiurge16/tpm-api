package net.nuclearprometheus.tpm.applicationserver.domain.validator

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError

class Validator {
    private val assertions = mutableListOf<() -> ValidationError>()

    class Assertion(val validator: Validator, val assertion: () -> Boolean) {
        infix fun otherwise(error: () -> ValidationError): Validator {
            if (!assertion()) {
                validator.assertions.add { error() }
            }
            return validator
        }
    }

    fun assert(assertion: () -> Boolean): Assertion {
        return Assertion(this, assertion)
    }

    fun assertIf(condition: () -> Boolean, assertion: () -> Boolean): Assertion {
        return Assertion(this) { condition() && assertion() }
    }

    fun validate(): List<ValidationError> {
        return assertions.map { it() }
    }
}