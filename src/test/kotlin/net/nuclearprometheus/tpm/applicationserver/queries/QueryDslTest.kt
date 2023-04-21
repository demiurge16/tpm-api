package net.nuclearprometheus.tpm.applicationserver.queries

import net.nuclearprometheus.tpm.applicationserver.queries.dsl.query
import net.nuclearprometheus.tpm.applicationserver.queries.operations.binary.*
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.AndOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.NotOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.logical.OrOperation
import net.nuclearprometheus.tpm.applicationserver.queries.operations.unary.NullComparison
import org.junit.jupiter.api.Test

class QueryDslTest {

    @Test
    fun `must build correct operation stack`() {
        // Given
        val dslQuery = query<Person> {
            not {
                or {
                    equals("name", "tom")
                    equals("name", "jerry")
                }
            }
            and {
                isNull("middlename")
            }
            and {
                equals("lastname", "smith")
            }
            or {
                and {
                    greaterThan("age", "18")
                    lessThan("age", "30")
                }
            }
            and {
                all("occupations", listOf("programmer", "developer"))
            }
            and {
                any("countries", listOf("USA", "UK"))
            }
            and {
                any("favouriteNumbers", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
            }
        }

        // When
        val correctStack = listOf(
            EqualsComparison<Person>("name", "tom"),
            EqualsComparison<Person>("name", "jerry"),
            OrOperation<Person>(),
            NotOperation<Person>(),
            NullComparison<Person>("middlename"),
            AndOperation<Person>(),
            EqualsComparison<Person>("lastname", "smith"),
            AndOperation<Person>(),
            GreaterThanComparison<Person>("age", "18"),
            LessThanComparison<Person>("age", "30"),
            AndOperation<Person>(),
            OrOperation<Person>(),
            AllComparison<Person>("occupations", listOf("programmer", "developer")),
            AndOperation<Person>(),
            AnyComparison<Person>("countries", listOf("USA", "UK")),
            AndOperation<Person>(),
            AnyComparison<Person>("favouriteNumbers", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")),
            AndOperation<Person>()
        )

        // Then
        assert(dslQuery.operationStack == correctStack)
    }

    @Test
    fun `must match entity`() {
        // Given
        val dslQuery = query<BusinessPerson> {
            not {
                or {
                    equals("name", "tom")
                    equals("name", "jerry")
                }
            }
            and {
                isNull("middlename")
            }
            and {
                equals("lastname", "smith")
            }
            or {
                and {
                    greaterThan("age", "18")
                    lessThan("age", "30")
                }
            }
            and {
                all("occupations", listOf("programmer", "developer"))
            }
            and {
                any("countries", listOf("USA", "UK"))
            }
            and {
                any("favouriteNumbers", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
            }
            and {
                equals("businessName", "ACME Inc.")
            }
            and {
                equals("businessCard.email", "tom.smith@acme.com")
            }
            and {
                or {
                    equals("businessCard.phone", "+1 123 456 789")
                    equals("businessCard.phone", "+3 1234 56789")
                }
            }
            and {
                equals("businessCard.fax", "+1 987 654 321")
            }
            and {
                contains("businessCard.website", "acme.com")
            }
        }

        val businessPerson = BusinessPerson(
            name = "tom",
            lastname = "smith",
            age = 20,
            occupations = listOf("programmer", "developer"),
            countries = listOf("USA", "UK"),
            favouriteNumbers = listOf(7, 16, 42),
            businessName = "ACME Inc.",
            businessCard = FancyBusinessCard(
                email = "tom.smith@acme.com",
                phone = "+1 123 456 789",
                fax = "+1 987 654 321",
                website = "acme.com"
            )
        )

        // When
        val result = dslQuery.evaluate(businessPerson)

        // Then
        assert(result)
    }

    @Test
    fun `empty query should return true`() {
        // Given
        val dslQuery = query<Person> {}

        val person = Person(
            name = "tom",
            lastname = "smith",
            age = 20,
            occupations = listOf("programmer", "developer"),
            countries = listOf("USA", "UK"),
            favouriteNumbers = listOf(7, 16, 42)
        )

        // When
        val result = dslQuery.evaluate(person)

        // Then
        assert(result)
    }
}
