package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.*
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.orderBy
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.sortSpecification
import org.junit.jupiter.api.Test
import java.util.*

class QueryTest {

    @Test
    fun `should build correct query tree`() {
        val queryTree = where {
            not {
                string(Person::name).eq("tom") or string(Person::name).eq("jerry")
            } and {
                string(Person::middlename).isNull()
            } and {
                string(Person::lastname).eq("smith")
            } or {
                comparable(Person::age).gt(18) and comparable(Person::age).lt(30)
            } and {
                collection(Person::occupations).any("programmer", "developer")
            } and {
                collection(Person::countries).any("USA", "UK")
            } and {
                collection(Person::hobbies).all("football", "basketball") or collection(Person::hobbies).none("tennis", "golf")
            } and {
                enum(Person::mood).eq(Mood.HAPPY)
            }
        }

        val expectedTree = Search(
            And(
                Not(
                    Or(
                        StringToken(Person::name).eq("tom"),
                        StringToken(Person::name).eq("jerry")
                    )
                ),
                And(
                    StringToken(Person::middlename).isNull(),
                    Or(
                        StringToken(Person::lastname).eq("smith"),
                        And(
                            And(
                                ComparableToken(Person::age, Int::class).gt(18),
                                ComparableToken(Person::age, Int::class).lt(30)
                            ),
                            And(
                                CollectionToken(Person::occupations, String::class).any("programmer", "developer"),
                                And(
                                    CollectionToken(Person::countries, String::class).any("USA", "UK"),
                                    And(
                                        Or(
                                            CollectionToken(Person::hobbies, String::class).all("football", "basketball"),
                                            CollectionToken(Person::hobbies, String::class).none("tennis", "golf")
                                        ),
                                        EnumToken(Person::mood, Mood::class).eq(Mood.HAPPY)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        assert(queryTree == expectedTree)
    }

    @Test
    fun `should build query from string using query specification`() {
        val queryString = """
            !(name:eq:"tom" | name:eq:"jerry")
            & middlename:null
            & lastname:eq:"smith"
            | (age:gt:18 & age:lt:30)
            & occupations:in:"programmer","developer"
            & countries:in:"USA","UK"
            & (hobbies:all:"football","basketball" | hobbies:none:"tennis","golf")
            & mood:eq:HAPPY
        """.trimIndent()

        val querySpecification = searchSpecification<Person> {
            uniqueToken("id", UUID::class) using Person::id
            string("name") using Person::name
            string("middlename") using Person::middlename
            string("lastname") using Person::lastname
            comparable("age", Int::class) using Person::age
            collection("occupations", String::class) using Person::occupations
            collection("countries", String::class) using Person::countries
            collection("favouriteNumbers", Int::class) using Person::favouriteNumbers
            collection("hobbies", String::class) using Person::hobbies
            enum("mood", Mood::class) using Person::mood
        }

        val query = createSearch(queryString, querySpecification)

        val queryTree = where {
            not {
                string(Person::name).eq("tom") or string(Person::name).eq("jerry")
            } and {
                string(Person::middlename).isNull()
            } and {
                string(Person::lastname).eq("smith")
            } or {
                comparable(Person::age).gt(18) and comparable(Person::age).lt(30)
            } and {
                collection(Person::occupations).any("programmer", "developer")
            } and {
                collection(Person::countries).any("USA", "UK")
            } and {
                collection(Person::hobbies).all("football", "basketball") or collection(Person::hobbies).none("tennis", "golf")
            } and {
                enum(Person::mood).eq(Mood.HAPPY)
            }
        }

        assert(query == queryTree)
    }

    @Test
    fun `should sort using sort specification`() {
        val sortSpecification = sortSpecification<Person> {
            sorter("name") using Comparator { o1: Person, o2: Person -> o1.name.compareTo(o2.name, ignoreCase = true) }
            sorter("middlename") using Comparator { o1: Person, o2: Person ->
                o1.middlename?.compareTo(o2.middlename ?: "", ignoreCase = true) ?: 0
            }
            sorter("lastname") using Comparator { o1: Person, o2: Person -> o1.lastname.compareTo(o2.lastname, ignoreCase = true) }
            sorter("age") using Comparator { o1: Person, o2: Person -> o1.age.compareTo(o2.age) }
        }

        val query = Query(
            sort = orderBy {
                ascending("name")
                descending("middlename")
                ascending("lastname")
                descending("age")
            },
            search = where {
                not {
                    string(Person::name).eq("tom") or string(Person::name).eq("jerry")
                } and {
                    string(Person::middlename).isNull()
                } and {
                    string(Person::lastname).eq("smith")
                } or {
                    comparable(Person::age).gt(18) and comparable(Person::age).lt(30)
                } and {
                    collection(Person::occupations).any("programmer", "developer")
                } and {
                    collection(Person::countries).any("USA", "UK")
                } and {
                    collection(Person::hobbies).all("football", "basketball") or collection(Person::hobbies).none("tennis", "golf")
                } and {
                    enum(Person::mood).eq(Mood.HAPPY)
                }
            }
        )

        PersonQueryExecutor.execute(query) {
            listOf(
                Person(name = "Tom", lastname = "Smith", age = 20, occupations = listOf("programmer"), countries = listOf("USA"), favouriteNumbers = listOf(1, 2, 3))
            )
        }
    }
}
