package net.nuclearprometheus.translationprojectmanager.domain.ports.services.client

import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientType
import net.nuclearprometheus.translationprojectmanager.domain.model.client.ClientTypeId
import java.util.UUID

interface ClientTypeService {
    fun create(name: String, description: String, corporate: Boolean): ClientType
    fun update(id: ClientTypeId, name: String, description: String, corporate: Boolean): ClientType
    fun activate(id: ClientTypeId): ClientType
    fun deactivate(id: ClientTypeId): ClientType
}