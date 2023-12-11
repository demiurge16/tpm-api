package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.Operator
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

    override fun createSpecification(field: String, operator: Operator, value: Any): Specification<Person> {
        return when (field) {
            "id" -> id.createSpecification(operator, value)
            "name" -> name.createSpecification(operator, value)
            "middlename" -> middlename.createSpecification(operator, value)
            "lastname" -> lastname.createSpecification(operator, value)
            "age" -> age.createSpecification(operator, value)
            "occupations" -> occupations.createSpecification(operator, value)
            "countries" -> countries.createSpecification(operator, value)
            "favouriteNumbers" -> favouriteNumbers.createSpecification(operator, value)
            "mood" -> mood.createSpecification(operator, value)
            else -> throw IllegalArgumentException("Unknown field $field")
        }
    }
}
