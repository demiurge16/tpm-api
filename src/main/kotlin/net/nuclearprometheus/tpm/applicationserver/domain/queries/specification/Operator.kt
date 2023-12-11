package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification

enum class Operator(val symbol: String) {
    EQUALS("eq"),
    CONTAINS("contains"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL("lte"),
    ALL("all"),
    ANY("any"),
    NONE("none"),
    IS_NULL("null"),
    IS_EMPTY("empty")
}
