package net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientType
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId

fun ClientTypeDatabaseModel.toDomain() = ClientType(
    id = ClientTypeId(id),
    name = name,
    description = description,
    corporate = corporate,
    active = active
)