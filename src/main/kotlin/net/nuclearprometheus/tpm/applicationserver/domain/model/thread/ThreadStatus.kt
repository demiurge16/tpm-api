package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

enum class ThreadStatus(val title: String, val description: String) {
    DRAFT("Draft", "Threads with this status are in the process of being created or edited"),
    ACTIVE("Active", "This is the default status for most threads. Threads with this status are active and visible to users"),
    FREEZE("Freeze", "This status is often used to prevent further comments or edits, while the content remains visible to users"),
    CLOSED("Closed", "No further comments or interactions are allowed, but the content remains visible for reference"),
    ARCHIVED("Archived", "They are moved to an archive for historical purposes and to declutter the active discussion areas"),
    DELETED("Deleted", " Threads with this status have been removed from the platform entirely"),
}
