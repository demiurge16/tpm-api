package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities

enum class ProjectStatusDatabaseModel {
    DRAFT,
    READY_TO_START,
    ACTIVE,
    ON_HOLD,
    READY_TO_DELIVER,
    DELIVERED,
    CANCELLED,
    INVOICED,
    PAID
}
