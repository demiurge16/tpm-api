package net.nuclearprometheus.tpm.applicationserver.domain.model.note

import java.util.UUID

data class NoteId(val value: UUID = UUID.randomUUID())
