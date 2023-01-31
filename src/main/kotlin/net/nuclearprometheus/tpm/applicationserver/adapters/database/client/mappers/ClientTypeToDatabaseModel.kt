package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType

fun ClientType.toDatabaseModel() = ClientTypeDatabaseModel(
    id = id.value,
    name = name,
    description = description,
    corporate = corporate,
    active = active
)
