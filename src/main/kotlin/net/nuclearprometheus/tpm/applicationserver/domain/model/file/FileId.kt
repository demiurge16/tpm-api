package net.nuclearprometheus.tpm.applicationserver.domain.model.file;

import java.util.UUID

data class FileId(val value: UUID = UUID.randomUUID());
