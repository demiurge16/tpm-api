package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients.SILInternationalCodeTablesClient
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.queryexecutors.LanguageQueryExecutor
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
class LanguageClientAdapter(
    private val client: SILInternationalCodeTablesClient,
    private val languageQueryExecutor: LanguageQueryExecutor
) : LanguageRepository {


    @Cacheable(value = ["languages-client-cache"], key = "'all'")
    override fun getAll() = client.iso6393CodeSet().map { it.toDomain() }


    @Cacheable(value = ["languages-client-cache"], key = "'query:' + #query.toString()")
    override fun get(query: Query<Language>) = languageQueryExecutor.execute(query) { getAll() }


    @Cacheable(value = ["languages-client-cache"], key = "'code:' + #code.value")
    override fun get(code: LanguageCode) = client.iso6393CodeSet()
        .filter { it.id == code.value }
        .map { it.toDomain() }
        .firstOrNull()


    @Cacheable(value = ["languages-client-cache"], key = "'codes:' + #codes")
    override fun get(codes: List<LanguageCode>) = client.iso6393CodeSet()
        .filter { it.id in codes.map { it.value } }
        .map { it.toDomain() }


    @Cacheable(value = ["languages-client-cache"], key = "'name:' + #name")
    override fun getByNameLike(name: String) = client.iso6393CodeSet()
        .filter { it.referenceName.contains(other = name, ignoreCase = true) }
        .map { it.toDomain() }
}
