package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operator

sealed class Token {
    class OpenParenthesis : Token()
    class CloseParenthesis : Token()
    class Not : Token()
    class And : Token()
    class Or : Token()
    class Comparison(val field: String, val operator: Operator, val value: String?) : Token()
}
