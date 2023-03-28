package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import java.io.InputStream

interface FileStorageService {
    fun store(location: String, name: String, dataStream: InputStream)
    fun delete(location: String, name: String)
}