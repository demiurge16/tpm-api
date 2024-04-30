package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Direction
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Order
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.Sort
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.SpecificationParser
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.CollectionSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.ComparableSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.Specification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications.StringSpecification
import org.junit.jupiter.api.Test

class QueryTest {

    @Test
    fun `must build correct specification with dsl`() {
        val search = query<Person> {
            where {
                with(PersonSpecification) {
                    not(name.eq("tom") or name.eq("jerry")) and middlename.isNull() and lastname.eq("smith") or (age.gt(18) and age.lt(30)) and occupations.all("programmer", "developer") and countries.any("USA", "UK") and favouriteNumbers.any(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                }
            }
        }

        val expected = Specification.AndSpecification(
            Specification.AndSpecification(
                Specification.AndSpecification(
                    Specification.OrSpecification(
                        Specification.AndSpecification(
                            Specification.AndSpecification(
                                Specification.NotSpecification(
                                    Specification.OrSpecification(
                                        StringSpecification.Eq<String>("name", "tom"),
                                        StringSpecification.Eq("name", "jerry")
                                    )
                                ),
                                StringSpecification.IsNull("middlename"),
                            ),
                            StringSpecification.Eq("lastname", "smith"),
                        ),
                        Specification.AndSpecification(
                            ComparableSpecification.Gt("age", 18),
                            ComparableSpecification.Lt("age", 30),
                        )
                    ),
                    CollectionSpecification.AllElement("occupations", listOf("programmer", "developer"))
                ),
                CollectionSpecification.AnyElement("countries", listOf("USA", "UK"))
            ),
            CollectionSpecification.AnyElement("favouriteNumbers", listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )

        assert(search.specification == expected)
    }

    @Test
    fun `must build correct specification with query string`() {
        val queryString = """
            !(name:eq:tom | name:eq:jerry)
            & middlename:null
            & lastname:eq:smith
            | (age:gt:18 & age:lt:30)
            & occupations:all:programmer,developer
            & countries:any:USA,UK
            & favouriteNumbers:any:1,2,3,4,5,6,7,8,9,10
        """.trimIndent()

        val specification = SpecificationParser.parseSpecification(queryString, PersonSpecification)
        val expected = query {
            where {
                with(PersonSpecification) {
                    not(name.eq("tom") or name.eq("jerry")) and middlename.isNull() and lastname.eq("smith") or (age.gt(18) and age.lt(30)) and occupations.all("programmer", "developer") and countries.any("USA", "UK") and favouriteNumbers.any(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                }
            }
        }

        assert(specification == expected.specification)
    }

    @Test
    fun `must match specification`() {
        val specification = query<Person> {
            where {
                with(PersonSpecification) {
                    not(name.eq("tom") or name.eq("jerry")) and middlename.isNull() and lastname.eq("smith") or (age.gt(18) and age.lt(30)) and occupations.all("programmer", "developer") and countries.any("USA", "UK") and favouriteNumbers.any(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                }
            }
        }

        val person = Person(
            name = "john",
            middlename = null,
            lastname = "smith",
            age = 25,
            occupations = listOf("programmer", "developer"),
            countries = listOf("USA", "UK"),
            favouriteNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        )

        PersonQueryExecutor.execute(specification, listOf(person))

        assert(PersonQueryExecutor.execute(specification, listOf(person)).items.contains(person))
    }

    @Test
    fun `must build correct sort with dsl`() {
        val search = query<Person> {
            orderBy(PersonSort) {
                name.ascending and age.descending
            }
        }

        val expected = Sort<Person>(
            listOf(
                Order("name", Direction.ASC),
                Order("age", Direction.DESC)
            )
        )

        assert(search.sort == expected)
    }
}
