package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.mappers;

import net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.dictionaries.models.CountryExternalModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.Name
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.Name.NativeName
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.InternationalDirectDialing
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.PostalCodeInfo
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country.Translation
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository


fun CountryExternalModel.toDomain(languageRepository: LanguageRepository) = Country(
    cca3 = CountryCode(cca3),
    cca2 = cca2,
    ccn3 = ccn3,
    name = Name(
        common = name.common,
        official = name.official,
        nativeName = name.nativeName.entries.associate { (key, value) ->
            LanguageCode(key) to NativeName(value.common, value.official)
        }
    ),
    topLevelDomains = tld,
    currencies = currencies.entries.associate { (key, value) ->
        CurrencyCode(key) to Currency(value.name, value.symbol)
    },
    internationalDirectDialing = InternationalDirectDialing(idd.root, idd.suffixes),
    capital = capital,
    altSpellings = altSpellings,
    languages = languageRepository.get(
        languages.entries.map { (key) -> LanguageCode(key) }
    ),
    translations = translations.entries.associate { (key, value) ->
        LanguageCode(key) to Translation(value.common, value.official)
    },
    flag = flag,
    postalCode = PostalCodeInfo(postalCode.format, postalCode.regex)
)
