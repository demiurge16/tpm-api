package net.nuclearprometheus.translationprojectmanager

import net.nuclearprometheus.translationprojectmanager.queries.*
import org.junit.jupiter.api.Test

class QueryParserTest {

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

        val query = parseQuery<Person>(queryString)

        assert(query.evaluate(person))
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

        val query = parseQuery<Person>(queryString)

        assert(query.evaluate(person))
    }
}



class Person(
    val name: String,
    val middlename: String? = null,
    val lastname: String,
    val age: Int,
    val occupations: List<String>,
    val countries: List<String>,
    val favouriteNumbers: List<Int>
)
