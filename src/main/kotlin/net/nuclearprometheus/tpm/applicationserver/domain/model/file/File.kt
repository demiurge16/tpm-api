package net.nuclearprometheus.tpm.applicationserver.domain.model.file

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import java.time.ZonedDateTime

class File(
    id: FileId = FileId(),
    val name: String,
    val size: Long,
    val type: String,
    val uploadTime: ZonedDateTime = ZonedDateTime.now(),
    val uploader: TeamMember,
    val projectId: ProjectId,
    val location: String
) : Entity<FileId>(id)
