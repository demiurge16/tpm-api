package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security

enum class ProjectScope {
    READ,
    UPDATE,
    MANAGE,
    QUERY_EXPENSES,
    EXPORT_EXPENSES,
    CREATE_EXPENSES,
    QUERY_FILES,
    EXPORT_FILES,
    CREATE_FILES,
    QUERY_TASKS,
    EXPORT_TASKS,
    CREATE_TASKS,
    QUERY_THREADS,
    CREATE_THREADS;
}