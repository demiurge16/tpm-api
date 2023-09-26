package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Country as CountryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

object CountryMapper {

    fun Country.toView() = CountryResponse(
        code = id.value,
        cca2 = cca2,
        ccn3 = ccn3,
        name = CountryResponse.Name(
            common = name.common,
            official = name.official,
            nativeName = name.nativeName.entries.associate { (k, v) ->
                k.value to CountryResponse.Name.NativeName(
                    official = v.official,
                    common = v.common
                )
            }
        ),
        topLevelDomains = topLevelDomains,
        currencies = currencies.entries.associate { (k, v) ->
            k.value to CountryResponse.Currency(
                name = v.name,
                symbol = v.symbol
            )
        },
        internationalDirectDialing = CountryResponse.InternationalDirectDialing(
            internationalDirectDialing.root,
            internationalDirectDialing.suffixes
        ),
        capital = capital,
        altSpellings = altSpellings,
        languages = languages.map { CountryResponse.Language(it.code.value, it.name) },
        translations = translations.entries.associate { (k, v) ->
            k.value to CountryResponse.Translation(
                official = v.official,
                common = v.common
            )
        },
        flag = flag,
        postalCode = CountryResponse.PostalCodeInfo(postalCode.format, postalCode.regex)
    )
}