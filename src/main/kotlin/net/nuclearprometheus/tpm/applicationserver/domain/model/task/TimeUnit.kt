package net.nuclearprometheus.tpm.applicationserver.domain.model.task

enum class TimeUnit(val title: String, val description: String, val multiplier: Int) {
    MINUTES("Minutes", "Minutes", 1),
    HOURS("Hours", "Hours (1 hour = 60 minutes)", 60),
    DAYS("Days", "Days (1 day = 8 hours)", 480),
}
