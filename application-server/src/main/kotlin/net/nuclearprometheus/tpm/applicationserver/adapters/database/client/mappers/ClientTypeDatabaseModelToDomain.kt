package net.nuclearprometheus.tpm.applicationserver.adapters.database.client.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.database.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientType
import net.nuclearprometheus.tpm.applicationserver.domain.model.client.ClientTypeId

fun ClientTypeDatabaseModel.toDomain() = ClientType(
    id = ClientTypeId(id),
    name = name,
    description = description,
    corporate = corporate,
    active = active
)