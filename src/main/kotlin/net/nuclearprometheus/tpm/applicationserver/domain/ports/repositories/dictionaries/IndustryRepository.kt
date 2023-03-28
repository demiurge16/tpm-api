package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface IndustryRepository : BaseRepository<Industry, IndustryId>
