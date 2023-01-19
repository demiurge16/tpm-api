package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries

import java.util.*

data class Industry(val id: UUID = UUID.randomUUID(), val name: String, val description: String)
