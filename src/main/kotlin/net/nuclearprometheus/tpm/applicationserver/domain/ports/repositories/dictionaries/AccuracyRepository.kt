package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
import org.bouncycastle.asn1.tsp.Accuracy

interface AccuracyRepository : BaseRepository<Accuracy, AccuracyId>
