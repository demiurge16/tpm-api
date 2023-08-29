package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.task.entities

enum class TaskStatusDatabaseModel {
    DRAFT,
    ASSIGNED,
    IN_PROGRESS,
    NEEDS_REVIEW,
    REVISIONS_NEEDED,
    COMPLETED,
    CANCELLED
}
