package net.nuclearprometheus.tpm.applicationserver.domain.model.task

enum class TaskStatus(val title: String, val description: String) {
    DRAFT("Draft", "The task has been created but is not yet ready to be worked on."),
    ASSIGNED("Assigned", "The task has been assigned to a team member and is ready to be worked on."),
    IN_PROGRESS("In Progress", "The team member is currently working on the task."),
    NEEDS_REVIEW("Needs Review", "The task has been completed and is awaiting review by a manager or subject matter expert."),
    REVISIONS_NEEDED("Revisions Needed", "The task has been reviewed and revisions are needed."),
    COMPLETED("Completed", "The task has been completed and is ready to be delivered to the project manager."),
    CANCELLED("Cancelled", "The task has been cancelled and will not be completed.")
}