package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses

import java.io.InputStream

data class Download(
    val file: File,
    val inputStream: InputStream
)
