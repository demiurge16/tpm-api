package net.nuclearprometheus.translationprojectmanager.adapters.database.client.mappers

import net.nuclearprometheus.translationprojectmanager.adapters.database.client.entities.ClientTypeDatabaseModel
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientType

fun ClientType.toDatabaseModel() = ClientTypeDatabaseModel(
    id = id.value,
    name = name,
    description = description,
    corporate = corporate,
    active = active
)
