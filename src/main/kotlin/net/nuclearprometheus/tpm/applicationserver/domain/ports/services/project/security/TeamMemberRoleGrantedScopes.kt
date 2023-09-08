package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.security

import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole

fun TeamMemberRole.getGrantedScopes(): Set<ProjectScope> {
    return when (this) {
        TeamMemberRole.PROJECT_MANAGER -> setOf(
            ProjectScope.READ,
            ProjectScope.UPDATE,
            ProjectScope.MANAGE,
            ProjectScope.QUERY_EXPENSES,
            ProjectScope.EXPORT_EXPENSES,
            ProjectScope.CREATE_EXPENSES,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.CREATE_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.CREATE_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
        TeamMemberRole.TRANSLATOR -> setOf(
            ProjectScope.READ,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.CREATE_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.CREATE_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
        TeamMemberRole.EDITOR -> setOf(
            ProjectScope.READ,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.CREATE_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.CREATE_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
        TeamMemberRole.PROOFREADER -> setOf(
            ProjectScope.READ,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.CREATE_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.CREATE_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
        TeamMemberRole.SUBJECT_MATTER_EXPERT -> setOf(
            ProjectScope.READ,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.CREATE_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.CREATE_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
        TeamMemberRole.PUBLISHER -> setOf(
            ProjectScope.READ,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.CREATE_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.CREATE_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
        TeamMemberRole.OBSERVER -> setOf(
            ProjectScope.READ,
            ProjectScope.QUERY_EXPENSES,
            ProjectScope.EXPORT_EXPENSES,
            ProjectScope.CREATE_EXPENSES,
            ProjectScope.QUERY_FILES,
            ProjectScope.EXPORT_FILES,
            ProjectScope.QUERY_TASKS,
            ProjectScope.EXPORT_TASKS,
            ProjectScope.QUERY_THREADS,
            ProjectScope.CREATE_THREADS
        )
    }
}
