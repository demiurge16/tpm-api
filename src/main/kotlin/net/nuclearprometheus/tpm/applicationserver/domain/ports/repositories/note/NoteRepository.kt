package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface NoteRepository : BaseRepository<Note, NoteId>