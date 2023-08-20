package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag as ThreadTag
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object ThreadMapper {

    fun Thread.toView(project: Project) = ThreadResponse.View(
        id = id.value,
        title = title,
        content = content,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        ),
        createdAt = createdAt,
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = ProjectStatus(
                status = project.status,
                title = project.status.title,
                description = project.status.description
            )
        ),
        status = Status(
            status = status,
            title = status.title,
            description = status.description
        ),
        likes = likes.map {
            Like(
                id = it.id.value,
                author = Author(
                    userId = it.author.id.value,
                    firstName = it.author.firstName,
                    lastName = it.author.lastName,
                    email = it.author.email
                ),
                createdAt = it.createdAt
            )
        },
        dislikes = dislikes.map {
            Dislike(
                id = it.id.value,
                author = Author(
                    userId = it.author.id.value,
                    firstName = it.author.firstName,
                    lastName = it.author.lastName,
                    email = it.author.email
                ),
                createdAt = it.createdAt
            )
        },
        tags = tags.map {
            Tag(
                id = it.id.value,
                name = it.name
            )
        }
    )

    fun Thread.toNewLike(author: User) = likes.first { it.author.id == author.id }.let {
        ThreadResponse.NewLike(
            id = id.value,
            author = Author(
                userId = author.id.value,
                firstName = author.firstName,
                lastName = author.lastName,
                email = author.email
            ),
            createdAt = createdAt
        )
    }

    fun Thread.toLikeRemoved(author: User) = ThreadResponse.LikeRemoved(
        id = id.value,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        ),
        createdAt = createdAt
    )

    fun Thread.toNewDislike(author: User) = dislikes.first { it.author.id == author.id }.let {
        ThreadResponse.NewDislike(
            id = id.value,
            author = Author(
                userId = author.id.value,
                firstName = author.firstName,
                lastName = author.lastName,
                email = author.email
            ),
            createdAt = createdAt
        )
    }

    fun Thread.toDislikeRemoved(author: User) = ThreadResponse.DislikeRemoved(
        id = id.value,
        author = Author(
            userId = author.id.value,
            firstName = author.firstName,
            lastName = author.lastName,
            email = author.email
        ),
        createdAt = createdAt
    )
}