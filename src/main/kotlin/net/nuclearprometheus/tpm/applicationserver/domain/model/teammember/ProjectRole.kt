package net.nuclearprometheus.tpm.applicationserver.domain.model.teammember

enum class ProjectRole(val title: String, val description: String) {
    PROJECT_MANAGER(
        "Project Manager",
        "Responsible for overseeing the project and ensuring its successful completion"
    ),
    TRANSLATOR(
        "Translator",
        "Responsible for translating the source text into the target language"
    ),
    EDITOR(
        "Editor",
        "Responsible for reviewing and revising the translated text for accuracy and style"
    ),
    PROOFREADER(
        "Proofreader",
        "Responsible for reviewing the translated text for errors and inconsistencies"
    ),
    SUBJECT_MATTER_EXPERT(
        "Subject Matter Expert",
        "Provides guidance and feedback on the translation of specific terms or concepts related to their area of expertise"
    ),
    PUBLISHER(
        "Publisher",
        "Responsible for formatting and layout of the translated content to match the original document, including formatting text, graphics, and other elements"
    ),
    OBSERVER(
        "Observer",
        "Has access to the project but does not have any responsibilities"
    )
}