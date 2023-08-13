package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities

enum class ThreadStatusDatabaseModel {
    DRAFT,
    ACTIVE,
    FREEZE,
    CLOSED,
    ARCHIVED,
    DELETED;
}