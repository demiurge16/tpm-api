package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors.LanguageQueryExecutor
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.SILInternationalCodeTablesClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import org.springframework.stereotype.Repository

@Repository
class LanguageClientAdapter(
    private val client: SILInternationalCodeTablesClient,
    private val languageQueryExecutor: LanguageQueryExecutor
) : LanguageRepository {
    override fun getAll() = client.iso6393CodeSet().map { it.toDomain() }

    override fun get(query: Query<Language>) = languageQueryExecutor.execute(query) { getAll() }

    override fun get(code: LanguageCode) = client.iso6393CodeSet()
        .filter { it.id == code.value }
        .map { it.toDomain() }
        .firstOrNull()

    override fun get(codes: List<LanguageCode>) = client.iso6393CodeSet()
        .filter { it.id in codes.map { it.value } }
        .map { it.toDomain() }

    override fun getByNameLike(name: String) = client.iso6393CodeSet()
        .filter { it.referenceName.contains(other = name, ignoreCase = true) }
        .map { it.toDomain() }
}
