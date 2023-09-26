package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.Thread as ThreadResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object ThreadMapper {

    fun Thread.toView(project: Project) = ThreadResponse(
        id = id.value,
        title = title,
        content = content,
        author = author.toView(),
        createdAt = createdAt,
        project = project.toShortView(),
        status = ThreadStatus(
            status = status,
            title = status.title,
            description = status.description
        ),
        likes = likes.map {
            Like(
                id = it.id.value,
                author = author.toView(),
                createdAt = it.createdAt
            )
        },
        dislikes = dislikes.map {
            Dislike(
                id = it.id.value,
                author = author.toView(),
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
        ThreadNewLike(
            id = id.value,
            author = author.toView(),
            createdAt = createdAt
        )
    }

    fun Thread.toLikeRemoved(author: User) = ThreadLikeRemoved(
        id = id.value,
        author = author.toView(),
        createdAt = createdAt
    )

    fun Thread.toNewDislike(author: User) = dislikes.first { it.author.id == author.id }.let {
        ThreadNewDislike(
            id = id.value,
            author = author.toView(),
            createdAt = createdAt
        )
    }

    fun Thread.toDislikeRemoved(author: User) = ThreadDislikeRemoved(
        id = id.value,
        author = author.toView(),
        createdAt = createdAt
    )
}
