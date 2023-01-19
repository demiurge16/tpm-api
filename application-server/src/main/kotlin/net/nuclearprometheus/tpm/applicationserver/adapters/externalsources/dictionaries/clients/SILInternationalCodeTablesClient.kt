package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.clients

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.toISO6393Language
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.toISO6393Macrolanguage
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.toISO6393Name
import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.serialization.toISO6393Retirement
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL

@Service
class SILInternationalCodeTablesClient(
    @Value("\${app.sil-international.client.code-set-url}") private val codeSetUrl: String,
    @Value("\${app.sil-international.client.names-url}") private val namesUrl: String,
    @Value("\${app.sil-international.client.macrolanguages-url}") private val macrolanguagesUrl: String,
    @Value("\${app.sil-international.client.retirements-url}") private val retirementsUrl: String,
) {

    fun iso6393CodeSet() = csvReader { delimiter = '\t' }
        .open(URL(codeSetUrl).openStream()) {
            readAllWithHeaderAsSequence().map { it.toISO6393Language() }.toList()
        }

    fun iso6393Names() = csvReader { delimiter = '\t' }
        .open(URL(namesUrl).openStream()) {
            readAllWithHeaderAsSequence().map { it.toISO6393Name() }.toList()
        }

    fun iso6393Macrolanguages() = csvReader { delimiter = '\t' }
        .open(URL(macrolanguagesUrl).openStream()) {
            readAllWithHeaderAsSequence().map { it.toISO6393Macrolanguage() }.toList()
        }

    fun iso6393Retirements() = csvReader { delimiter = '\t' }
        .open(URL(retirementsUrl).openStream()) {
            readAllWithHeaderAsSequence().map { it.toISO6393Retirement() }.toList()
        }
}
