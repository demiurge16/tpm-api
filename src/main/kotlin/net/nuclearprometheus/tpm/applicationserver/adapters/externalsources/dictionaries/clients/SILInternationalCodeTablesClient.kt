package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients

import com.opencsv.bean.CsvToBeanBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Language
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Macrolanguage
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Name
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.ISO6393Retirement
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.net.URL

@Service
class SILInternationalCodeTablesClient(
    @Value("\${app.sil-international.client.code-set-url}") private val codeSetUrl: String,
    @Value("\${app.sil-international.client.names-url}") private val namesUrl: String,
    @Value("\${app.sil-international.client.macrolanguages-url}") private val macrolanguagesUrl: String,
    @Value("\${app.sil-international.client.retirements-url}") private val retirementsUrl: String,
) {

    @Cacheable(value = ["languages-client-cache"], key = "'iso-639-3-code-set'")
    fun iso6393CodeSet(): List<ISO6393Language> =
        CsvToBeanBuilder<ISO6393Language>(URL(codeSetUrl).openStream().reader())
            .withSeparator('\t')
            .withType(ISO6393Language::class.java)
            .build()
            .parse()
            .toList()

    @Cacheable(value = ["languages-client-cache"], key = "'iso-639-3-names'")
    fun iso6393Names(): List<ISO6393Name> =
        CsvToBeanBuilder<ISO6393Name>(URL(namesUrl).openStream().reader())
            .withSeparator('\t')
            .withType(ISO6393Name::class.java)
            .build()
            .parse()
            .toList()

    @Cacheable(value = ["languages-client-cache"], key = "'iso-639-3-macrolanguages'")
    fun iso6393Macrolanguages(): List<ISO6393Macrolanguage> =
        CsvToBeanBuilder<ISO6393Macrolanguage>(URL(macrolanguagesUrl).openStream().reader())
            .withSeparator('\t')
            .withType(ISO6393Macrolanguage::class.java)
            .build()
            .parse()
            .toList()

    @Cacheable(value = ["languages-client-cache"], key = "'iso-639-3-retirements'")
    fun iso6393Retirements(): List<ISO6393Retirement> =
        CsvToBeanBuilder<ISO6393Retirement>(URL(retirementsUrl).openStream().reader())
            .withSeparator('\t')
            .withType(ISO6393Retirement::class.java)
            .build()
            .parse()
            .toList()
}
