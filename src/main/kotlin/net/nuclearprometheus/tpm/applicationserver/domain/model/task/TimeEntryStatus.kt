package net.nuclearprometheus.tpm.applicationserver.domain.model.task

enum class TimeEntryStatus(val title: String, val description: String) {
    DRAFT("Draft", "Not submitted yet"),
    SUBMITTED("Submitted", "Submitted for approval"),
    APPROVED("Approved", "Approved by the manager"),
    REJECTED("Rejected", "Rejected by the manager")
}
