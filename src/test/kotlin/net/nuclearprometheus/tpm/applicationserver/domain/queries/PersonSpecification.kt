package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.*

object PersonSpecification : SpecificationBuilder<Person>() {
    val id = uniqueValue("id", UUID::class)
    val name = string("name")
    val middlename = string("middlename")
    val lastname = string("lastname")
    val age = comparable("age", Int::class)
    val occupations = collection("occupations", String::class)
    val countries = collection("countries", String::class)
    val favouriteNumbers = collection("favouriteNumbers", Int::class)
    val mood = enum("mood", Mood::class)
}
