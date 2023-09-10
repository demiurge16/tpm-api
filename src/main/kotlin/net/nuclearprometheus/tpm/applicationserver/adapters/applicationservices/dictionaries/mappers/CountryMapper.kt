package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.CountryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

object CountryMapper {

    fun Country.toView() = CountryResponse.Country(
        code = id.value,
        cca2 = cca2,
        ccn3 = ccn3,
        name = CountryResponse.Country.Name(
            common = name.common,
            official = name.official,
            nativeName = name.nativeName.entries.associate { (k, v) ->
                k.value to CountryResponse.Country.Name.NativeName(
                    official = v.official,
                    common = v.common
                )
            }
        ),
        topLevelDomains = topLevelDomains,
        currencies = currencies.entries.associate { (k, v) ->
            k.value to CountryResponse.Country.Currency(
                name = v.name,
                symbol = v.symbol
            )
        },
        internationalDirectDialing = CountryResponse.Country.InternationalDirectDialing(
            internationalDirectDialing.root,
            internationalDirectDialing.suffixes
        ),
        capital = capital,
        altSpellings = altSpellings,
        languages = languages.map { CountryResponse.Country.Language(it.code.value, it.name) },
        translations = translations.entries.associate { (k, v) ->
            k.value to CountryResponse.Country.Translation(
                official = v.official,
                common = v.common
            )
        },
        flag = flag,
        postalCode = CountryResponse.Country.PostalCodeInfo(postalCode.format, postalCode.regex)
    )
}