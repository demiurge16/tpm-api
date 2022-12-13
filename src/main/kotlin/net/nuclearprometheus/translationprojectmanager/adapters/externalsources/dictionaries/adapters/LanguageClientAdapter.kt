package net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.clients.SILInternationalCodeTablesClient
import net.nuclearprometheus.translationprojectmanager.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.LanguageRepository
import org.springframework.stereotype.Repository

@Repository
class LanguageClientAdapter(private val client: SILInternationalCodeTablesClient) : LanguageRepository {
    override fun getAll() = client.iso6393CodeSet().map { it.toDomain() }

    override fun getByCode(code: LanguageCode) = client.iso6393CodeSet()
        .filter { it.id == code.value }
        .map { it.toDomain() }
        .firstOrNull()

    override fun getByNameLike(name: String) = client.iso6393CodeSet()
        .filter { it.referenceName.contains(other = name, ignoreCase = true) }
        .map { it.toDomain() }
}
