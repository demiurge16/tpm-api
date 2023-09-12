package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities

enum class TaskStatusDatabaseModel {
    DRAFT,
    ASSIGNED,
    IN_PROGRESS,
    IN_REVIEW,
    ON_HOLD,
    READY_TO_DELIVER,
    COMPLETED,
    CANCELLED
}
