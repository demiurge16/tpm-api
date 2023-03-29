package net.nuclearprometheus.tpm.applicationserver.adapters.files

import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileStorageService
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class MinioStorageService(
    private val minioClient: MinioClient
) : FileStorageService {

    override fun load(location: String, name: String): InputStream {
        return minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(location)
                .`object`(name)
                .build()
        )
    }

    override fun store(location: String, name: String, dataStream: InputStream) {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(location)
                .`object`(name)
                .stream(dataStream, dataStream.available().toLong(), -1)
                .build()
        )
    }

    override fun delete(location: String, name: String) {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(location)
                .`object`(name)
                .build()
        )
    }
}