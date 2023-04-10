package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.file

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/file")
class FileController {

    @GetMapping("")
    fun getFiles() {
        TODO()
    }

    @GetMapping("/{fileId}")
    fun getFile(@PathVariable(name = "fileId") fileId: UUID) {
        TODO()
    }

    @GetMapping("/{fileId}/download")
    fun downloadFile(@PathVariable(name = "fileId") fileId: UUID) {
        TODO()
    }

    @DeleteMapping("/{fileId}")
    fun deleteFile(@PathVariable(name = "fileId") fileId: UUID) {
        TODO()
    }
}