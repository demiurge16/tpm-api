package net.nuclearprometheus.tpm.applicationserver.domain.queries

import java.util.*

open class Person(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val middlename: String? = null,
    val lastname: String,
    val age: Int,
    val occupations: List<String>,
    val countries: List<String>,
    val favouriteNumbers: List<Int>,
    val hobbies: List<String> = emptyList(),
    val mood: Mood = Mood.HAPPY
) {
    override fun toString(): String {
        return "Person(id=$id, name='$name', middlename=$middlename, lastname='$lastname', age=$age, occupations=$occupations, countries=$countries, favouriteNumbers=$favouriteNumbers, hobbies=$hobbies, mood=$mood)"
    }
}
