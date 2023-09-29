package net.nuclearprometheus.tpm.applicationserver.domain.model.file

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate
import java.time.ZonedDateTime

class File(
    id: FileId = FileId(),
    val name: String,
    val size: Long,
    val type: String,
    val uploadTime: ZonedDateTime = ZonedDateTime.now(),
    val uploader: User,
    val projectId: ProjectId,
    val location: String
) : Entity<FileId>(id) {

    init {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { size > 0 } otherwise {
                ValidationError("size", "Size must be greater than zero")
            }
            assert { type.isNotBlank() } otherwise {
                ValidationError("type", "Type cannot be blank")
            }
            assert { location.isNotBlank() } otherwise {
                ValidationError("location", "Location cannot be blank")
            }
        }
    }
}
