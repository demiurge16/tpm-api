package net.nuclearprometheus.tpm.applicationserver.domain.model.project

enum class ProjectStatus(val title: String, val description: String) {
    DRAFT("Draft", "The project has been created but not yet ready to start."),
    READY_TO_START("Ready to Start", "The project has been approved and ready to start."),
    ACTIVE("Active", "The project is currently in progress."),
    ON_HOLD("On Hold", "The project is temporarily on hold due to some issue or delay."),
    READY_TO_DELIVER("Ready to Deliver", "The project has been completed and ready for delivery."),
    DELIVERED("Delivered", "The project has been delivered to the client."),
    CANCELLED("Cancelled", "The project has been cancelled and will not be completed."),
    INVOICED("Invoiced", "The project has been invoiced to the client."),
    PAID("Paid", "The client has paid for the project.")
}
