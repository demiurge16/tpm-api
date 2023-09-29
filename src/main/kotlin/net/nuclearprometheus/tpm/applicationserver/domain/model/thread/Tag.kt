package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate

class Tag(
    id: TagId = TagId(),
    name: String,
    threadId: ThreadId
) : Entity<TagId>(id) {

    init {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
        }
    }

    var name = name; private set
    var threadId = threadId; private set
}