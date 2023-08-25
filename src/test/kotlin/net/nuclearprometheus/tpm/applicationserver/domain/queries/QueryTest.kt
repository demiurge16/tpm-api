package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.Operation
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.search
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.sort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class QueryTest {

    @Test
    fun `must build correct operation stack with string`() {
        val queryString = """
            !(name:eq:"tom" | name:eq:"jerry")
            & middlename:null
            & lastname:eq:"smith"
            | (age:gt:18 & age:lt:30)
            & occupations:all:"programmer","developer"
            & countries:any:"USA","UK"
            & favouriteNumbers:any:1,2,3,4,5,6,7,8,9,10
        """.trimIndent()

        // When
        val stringQuery = Query<Person>(search = createSearch(queryString))
        val correctStack = listOf<Operation<Person>>(
            Operation.Equals("name", "tom"),
            Operation.Equals("name", "jerry"),
            Operation.Or(),
            Operation.Not(),
            Operation.IsNull("middlename"),
            Operation.And(),
            Operation.Equals("lastname", "smith"),
            Operation.And(),
            Operation.GreaterThan("age", "18"),
            Operation.LessThan("age", "30"),
            Operation.And(),
            Operation.Or(),
            Operation.AllElements("occupations", listOf("programmer", "developer")),
            Operation.And(),
            Operation.AnyElement("countries", listOf("USA", "UK")),
            Operation.And(),
            Operation.AnyElement("favouriteNumbers", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")),
            Operation.And()
        )

        // Then
        assert(stringQuery.search.operationStack == correctStack)
    }

    @Test
    fun `must build correct operation stack with builder`() {
        // Given
        val dslQuery = Query<Person>(
            search = search {
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
        )

        // When
        val correctStack = listOf<Operation<Person>>(
            Operation.Equals("name", "tom"),
            Operation.Equals("name", "jerry"),
            Operation.Or(),
            Operation.Not(),
            Operation.IsNull("middlename"),
            Operation.And(),
            Operation.Equals("lastname", "smith"),
            Operation.And(),
            Operation.GreaterThan("age", "18"),
            Operation.LessThan("age", "30"),
            Operation.And(),
            Operation.Or(),
            Operation.AllElements("occupations", listOf("programmer", "developer")),
            Operation.And(),
            Operation.AnyElement("countries", listOf("USA", "UK")),
            Operation.And(),
            Operation.AnyElement("favouriteNumbers", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")),
            Operation.And()
        )

        // Then
        assert(dslQuery.search.operationStack == correctStack)
    }

    @Test
    fun `must match entity`() {
        val queryString = """
            !(name:eq:"tom" | name:eq:"jerry")
            & middlename:null
            & lastname:eq:"smith"
            | (age:gt:18 & age:lt:30)
            & occupations:all:"programmer","developer"
            & countries:any:"USA","UK"
            & favouriteNumbers:any:1,2,3,4,5,6,7,8,9,10
        """.trimIndent()

        val person = Person(
            name = "tom",
            lastname = "smith",
            age = 20,
            occupations = listOf("programmer", "developer"),
            countries = listOf("USA", "UK"),
            favouriteNumbers = listOf(7, 16, 42)
        )

        val query = Query<Person>(search = createSearch(queryString))
        val result = PersonQueryExecutor().execute(query, listOf(person))

        assert(result.items.size == 1)
        assert(result.items[0] == person)
    }

    @Test
    fun `empty query should return true`() {
        val queryString = ""
        val person = Person(
            name = "tom",
            lastname = "smith",
            age = 20,
            occupations = listOf("programmer", "developer"),
            countries = listOf("USA", "UK"),
            favouriteNumbers = listOf(7, 16, 42)
        )

        val query = Query<Person>(search = createSearch(queryString))
        val result = PersonQueryExecutor().execute(query, listOf(person))

        assert(result.items.size == 1)
        assert(result.items[0] == person)
    }

    @Test
    fun `should sort by name`() {
        val query = Query<Person>(
            sort = sort {
                ascending("name")
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
        ))

        Assertions.assertEquals("Alice", result.items[0].name, "First person should be Alice")
        Assertions.assertEquals("Bob", result.items[1].name, "Second person should be Bob")
        Assertions.assertEquals("Jake", result.items[2].name, "Third person should be Jake")
        Assertions.assertEquals("John", result.items[3].name, "Fourth person should be John")
    }

    @Test
    fun `should sort by name descending`() {
        val query = Query<Person>(
            sort = sort {
                descending("name")
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
        ))

        Assertions.assertEquals("John", result.items[0].name, "First person should be John")
        Assertions.assertEquals("Jake", result.items[1].name, "Second person should be Jake")
        Assertions.assertEquals("Bob", result.items[2].name, "Third person should be Bob")
        Assertions.assertEquals("Alice", result.items[3].name, "Fourth person should be Alice")
    }

    @Test
    fun `should sort by name and age`() {
        val query = Query<Person>(
            sort = sort {
                ascending("name")
                descending("age")
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
        ))

        Assertions.assertEquals("Alice", result.items[0].name, "First person should be Alice")
        Assertions.assertEquals("Bob", result.items[1].name, "Second person should be Bob")
        Assertions.assertEquals("Jake", result.items[2].name, "Third person should be Jake")
        Assertions.assertEquals("John", result.items[3].name, "Fourth person should be John")
    }

    @Test
    fun `should filter by mood`() {
        val query = Query<Person>(
            search = search {
                equals("mood", Mood.BORED)
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf(), Mood.BORED),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(1, result.items.size, "Should be 1 result")
        Assertions.assertEquals("Alice", result.items[0].name, "First person should be Alice")
    }

    @Test
    fun `should filter by occupations`() {
        val query = Query<Person>(
            search = search {
                any("occupations", listOf("Developer"))
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
            Person("Tom", "Doe", "Smith", 10, listOf("Programmer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(4, result.items.size, "Should be 4 results")
        Assertions.assertEquals("John", result.items[0].name, "First person should be John")
        Assertions.assertEquals("Alice", result.items[1].name, "Second person should be Alice")
        Assertions.assertEquals("Bob", result.items[2].name, "Third person should be Bob")
        Assertions.assertEquals("Jake", result.items[3].name, "Fourth person should be Jake")
    }

    @Test
    fun `should filter by age greater than`() {
        val query = Query<Person>(
            search = search {
                greaterThan("age", "20")
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
            Person("Tom", "Doe", "Smith", 10, listOf("Programmer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(2, result.items.size, "Should be 2 results")
        Assertions.assertEquals("John", result.items[0].name, "First person should be John")
        Assertions.assertEquals("Bob", result.items[1].name, "Second person should be Bob")
    }

    @Test
    fun `should filter by age greater than and less than`() {
        val query = Query<Person>(
            search = search {
                and {
                    greaterThan("age", "20")
                    lessThan("age", "40")
                }
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
            Person("Tom", "Doe", "Smith", 10, listOf("Programmer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(1, result.items.size, "Should be 1 result")
        Assertions.assertEquals("John", result.items[0].name, "First person should be John")
    }

    @Test
    fun `should filter by age greater than equals or less than equals`() {
        val query = Query<Person>(
            search = search {
                or {
                    lessThanOrEqual("age", "20")
                    greaterThanOrEqual("age", "40")
                }
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
            Person("Tom", "Doe", "Smith", 10, listOf("Programmer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(4, result.items.size, "Should be 4 results")
        Assertions.assertEquals("Alice", result.items[0].name, "First person should be Alice")
        Assertions.assertEquals("Bob", result.items[1].name, "Second person should be Bob")
        Assertions.assertEquals("Jake", result.items[2].name, "Third person should be Jake")
        Assertions.assertEquals("Tom", result.items[3].name, "Fourth person should be Tom")
    }

    @Test
    fun `should filter by none occupation`() {
        val query = Query<Person>(
            search = search {
                none("occupations", listOf("Developer"))
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer"), listOf(), listOf()),
            Person("Tom", "Doe", "Smith", 10, listOf("Programmer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(1, result.items.size, "Should be 1 result")
        Assertions.assertEquals("Tom", result.items[0].name, "First person should be Tom")
    }

    @Test
    fun `should filter by all occupations`() {
        val query = Query<Person>(
            search = search {
                all("occupations", listOf("Developer", "Programmer"))
            }
        )
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer", "Designer"), listOf(), listOf()),
            Person("Alice", "Doe", "Smith", 20, listOf("Developer", "Programmer"), listOf(), listOf()),
            Person("Bob", "Doe", "Smith", 40, listOf("Developer"), listOf(), listOf()),
            Person("Jake", "Doe", "Smith", 10, listOf("Developer", "Programmer"), listOf(), listOf()),
            Person("Tom", "Doe", "Smith", 10, listOf("Programmer"), listOf(), listOf()),
        ))

        Assertions.assertEquals(2, result.items.size, "Should be 2 results")
        Assertions.assertEquals("Alice", result.items[0].name, "First person should be Alice")
        Assertions.assertEquals("Jake", result.items[1].name, "Second person should be Jake")
    }
}
