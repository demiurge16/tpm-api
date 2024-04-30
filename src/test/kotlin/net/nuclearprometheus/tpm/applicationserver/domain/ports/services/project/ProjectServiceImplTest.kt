package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.client.ClientRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.math.BigDecimal
import java.time.ZonedDateTime

class ProjectServiceImplTest {

    @Test
    fun `moveDeadlines should throw an exception if deadline is before start date`() {
        // Arrange
        val user = User(
            id = UserId(),
            firstName = "Test",
            lastName = "User",
            username = "testuser",
            email = "testuser@testorg.com",
            roles = listOf(UserRole.ADMIN)
        )

        val projectId = ProjectId()
        val project = Project(
            id = projectId,
            title = "Test project",
            description = "Test description",
            sourceLanguage = mock(),
            targetLanguages = listOf(
                mock(),
                mock()
            ),
            industry = mock {
                on { active } doReturn true
            },
            accuracy = mock {
                on { active } doReturn true
            },
            unit = mock {
                on { active } doReturn true
            },
            serviceTypes = listOf(
                mock {
                    on { active } doReturn true
                },
                mock {
                    on { active } doReturn true
                }
            ),
            amount = 1000,
            expectedStart = ZonedDateTime.now(),
            internalDeadline = ZonedDateTime.now().plusDays(10),
            externalDeadline = ZonedDateTime.now().plusDays(20),
            budget = BigDecimal(1000),
            currency = mock(),
            teamMembers = listOf(
                TeamMember(
                    user = user,
                    roles = listOf(
                        TeamMemberRole(
                            role = ProjectRole.PROJECT_MANAGER,
                            userId = user.id,
                            projectId = projectId
                        )
                    ),
                    projectId = projectId
                )
            ),
            client = mock {
                on { active } doReturn true
            }
        )

        val projectRepository = mock<ProjectRepository> {
            on { get(projectId) } doReturn project
        }
        val languageRepository = mock<LanguageRepository>()
        val accuracyRepository = mock<AccuracyRepository>()
        val industryRepository = mock<IndustryRepository>()
        val unitRepository = mock<UnitRepository>()
        val serviceTypeRepository = mock<ServiceTypeRepository>()
        val currencyRepository = mock<CurrencyRepository>()
        val clientRepository = mock<ClientRepository>()
        val userContextProvider = mock<UserContextProvider> {
            on { getCurrentUser() } doReturn user
        }
        val logger = mock<Logger>()

        val projectService = ProjectServiceImpl(
            projectRepository = projectRepository,
            languageRepository = languageRepository,
            accuracyRepository = accuracyRepository,
            industryRepository = industryRepository,
            unitRepository = unitRepository,
            serviceTypeRepository = serviceTypeRepository,
            currencyRepository = currencyRepository,
            clientRepository = clientRepository,
            userContextProvider = userContextProvider,
            logger = logger
        )


        // Act
        val exception = assertThrows<ValidationException> {
            projectService.moveDeadlines(projectId, ZonedDateTime.now().minusDays(20), ZonedDateTime.now().minusDays(10))
        }

        // Assert
        assertEquals(
            listOf(ValidationError("internalDeadline", "Internal deadline must be after expected start")),
            exception.errors
        )
    }
}