package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface AccuracyRepository : BaseRepository<Accuracy, AccuracyId>
