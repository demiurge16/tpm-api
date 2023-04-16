package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.file

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.FileApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/file")
class FileController(private val service: FileApplicationService) {

    private val logger = loggerFor(FileController::class.java)

    @GetMapping("")
    fun getFiles() = with(logger) {
        info("GET /api/v1/file")

        ResponseEntity.ok().body(service.getFiles())
    }

    @GetMapping("/{fileId}")
    fun getFile(@PathVariable(name = "fileId") fileId: UUID) = with(logger) {
        info("GET /api/v1/file/$fileId")

        ResponseEntity.ok().body(service.getFile(fileId))
    }

    @GetMapping("/{fileId}/download", produces = ["application/octet-stream"])
    fun downloadFile(@PathVariable(name = "fileId") fileId: UUID) = with(logger) {
        info("GET /api/v1/file/$fileId/download")

        val (name, stream) = service.downloadFile(fileId)
        val resource = InputStreamResource(stream)

        ResponseEntity.ok()
            .headers(
                HttpHeaders().apply {
                    add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
                    add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    add(HttpHeaders.PRAGMA, "no-cache")
                    add(HttpHeaders.EXPIRES, "0")
                }
            )
            .contentLength(stream.available().toLong())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource)
    }

    @DeleteMapping("/{fileId}")
    fun deleteFile(@PathVariable(name = "fileId") fileId: UUID) = with(logger) {
        info("DELETE /api/v1/file/$fileId")

        service.deleteFile(fileId)
        ResponseEntity.noContent().build<Void>()
    }
}
