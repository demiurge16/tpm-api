package net.nuclearprometheus.tpm.applicationserver.domain.queries

open class Person(
    val name: String,
    val middlename: String? = null,
    val lastname: String,
    val age: Int,
    val occupations: List<String>,
    val countries: List<String>,
    val favouriteNumbers: List<Int>,
    val mood: Mood = Mood.HAPPY
) {
    override fun toString(): String {
        return "Person(name='$name', middlename=$middlename, lastname='$lastname', age=$age, occupations=$occupations, countries=$countries, favouriteNumbers=$favouriteNumbers, mood=$mood)"
    }
}
