package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.mappers.ThreadStatusMapper.toThreadStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses.ThreadNewStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread.ThreadService
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ThreadStatusApplicationService(private val service: ThreadService) {

    private val logger = loggerFor(ThreadStatusApplicationService::class.java)

    fun activate(id: UUID): ThreadNewStatus {
        logger.info("activate($id)")
        return service.activate(ThreadId(id)).toThreadStatusResponse()
    }

    fun freeze(id: UUID): ThreadNewStatus {
        logger.info("freeze($id)")
        return service.freeze(ThreadId(id)).toThreadStatusResponse()
    }

    fun close(id: UUID): ThreadNewStatus {
        logger.info("close($id)")
        return service.close(ThreadId(id)).toThreadStatusResponse()
    }

    fun archive(id: UUID): ThreadNewStatus {
        logger.info("archive($id)")
        return service.archive(ThreadId(id)).toThreadStatusResponse()
    }

    fun delete(id: UUID): ThreadNewStatus {
        logger.info("delete($id)")
        return service.delete(ThreadId(id)).toThreadStatusResponse()
    }
}