package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

enum class Measurement(val title: String, val description: String) {
    CHARACTERS("Characters", "Characters (e.g. letters, numbers, symbols)"),
    POINTS("Points", "Points - abstract measurement, used to measure complexity, difficulty of a task or project. Inspired by the system used by the Scrum framework"),
    MINUTES("Minutes", "Minutes - time measurement. 1 minute = 60 seconds"),
    HOURS("Hours", "Hours - time measurement. 1 hour = 60 minutes"),
}
