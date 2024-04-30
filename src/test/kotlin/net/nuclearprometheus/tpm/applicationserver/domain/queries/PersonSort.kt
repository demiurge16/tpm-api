package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.OrderByBuilder

object PersonSort : OrderByBuilder<Person>() {
    val name = sort("name")
    val middlename = sort("middlename")
    val lastname = sort("lastname")
    val age = sort("age")
    val occupations = sort("occupations")
    val countries = sort("countries")
    val favouriteNumbers = sort("favouriteNumbers")
    val mood = sort("mood")
}