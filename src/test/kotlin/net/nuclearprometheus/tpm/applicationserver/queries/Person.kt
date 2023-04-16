package net.nuclearprometheus.tpm.applicationserver.queries

class Person(
    val name: String,
    val middlename: String? = null,
    val lastname: String,
    val age: Int,
    val occupations: List<String>,
    val countries: List<String>,
    val favouriteNumbers: List<Int>
)
