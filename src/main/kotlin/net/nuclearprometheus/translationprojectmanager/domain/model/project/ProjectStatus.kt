package net.nuclearprometheus.translationprojectmanager.domain.model.project

enum class ProjectStatus(val title: String) {
    DRAFT("Draft"),
    READY_TO_START("Ready to start"),
    IN_PROGRESS("In progress"),
    READY_TO_DELIVER("Ready to deliver"),
    DELIVERED("Delivered"),
    INVOICED("Invoiced"),
    PAID("Paid"),
    CANCELLED("Cancelled")
}
