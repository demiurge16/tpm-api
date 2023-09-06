package net.nuclearprometheus.tpm.applicationserver.domain.model.user

enum class UserRole(val role: String, val title: String, val description: String) {
    ADMIN(
        "admin",
        "Admin",
        "Admin has access to every function on platform"
    ),
    PROJECT_MANAGER(
        "project-manager",
        "Project manager",
        "Project manager can manage all projects he is assigned to and create new projects"
    ),
    TRANSLATOR(
        "translator",
        "Translator",
        "Translator can be assigned to a project task and manage tasks he is assigned to"
    ),
    EDITOR(
        "editor",
        "Editor",
        "Editor can be assigned to a project task and manage tasks he is assigned to"
    ),
    PROOFREADER(
        "proofreader",
        "Proofreader",
        "Proofreader can be assigned to a project task and manage tasks he is assigned to"
    ),
    SUBJECT_MATTER_EXPERT(
        "subject-matter-expert",
        "Subject matter expert",
        "Subject matter expert can be assigned to a project task and manage tasks he is assigned to"
    ),
    PUBLISHER(
        "publisher",
        "Publisher",
        "Publisher can be assigned to a project task and manage tasks he is assigned to"
    ),
    OBSERVER(
        "observer",
        "Observer",
        "Observer can observe projects he is invited to"
    ),
    USER(
        "user",
        "User",
        "User can log in and see his profile"
    )
}
