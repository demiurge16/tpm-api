package net.nuclearprometheus.translationprojectmanager.queries.operations

enum class OperationType(val symbol: String) {
    EQUALS("eq"),
    CONTAINS("contains"),
    GREATER_THAN("gt"),
    LESS_THAN("lt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN_OR_EQUAL("lte"),
    ANY("any"),
    ALL("all"),
    IS_NULL("null"),
    IS_EMPTY("empty");
}
