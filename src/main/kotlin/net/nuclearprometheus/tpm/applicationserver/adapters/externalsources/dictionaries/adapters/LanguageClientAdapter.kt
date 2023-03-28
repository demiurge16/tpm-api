package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.SILInternationalCodeTablesClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import org.springframework.stereotype.Repository

@Repository
class LanguageClientAdapter(private val client: SILInternationalCodeTablesClient) : LanguageRepository {
    override fun getAll() = client.iso6393CodeSet().map { it.toDomain() }

    override fun get(code: LanguageCode) = client.iso6393CodeSet()
        .filter { it.id == code.value }
        .map { it.toDomain() }
        .firstOrNull()

    override fun getByNameLike(name: String) = client.iso6393CodeSet()
        .filter { it.referenceName.contains(other = name, ignoreCase = true) }
        .map { it.toDomain() }
}
