package net.nuclearprometheus.tpm.applicationserver.config.files

import io.minio.MinioClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.file-storage.minio")
class MinioClientConfig(
    var endpoint: String = "",
    var accessKey: String = "",
    var secretKey: String = "",
) {

    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint(endpoint)
        .credentials(accessKey, secretKey)
        .build()

}
