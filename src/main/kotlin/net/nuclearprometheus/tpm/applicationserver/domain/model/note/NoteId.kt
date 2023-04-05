package net.nuclearprometheus.tpm.applicationserver.domain.model.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.UUID

class NoteId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
