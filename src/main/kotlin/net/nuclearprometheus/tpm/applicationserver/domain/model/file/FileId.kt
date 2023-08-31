package net.nuclearprometheus.tpm.applicationserver.domain.model.file;

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class FileId(value: UUID = UUID.randomUUID()): Id<UUID>(value)
