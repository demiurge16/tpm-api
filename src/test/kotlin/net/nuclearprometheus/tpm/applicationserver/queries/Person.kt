package net.nuclearprometheus.tpm.applicationserver.queries

open class Person(
    val name: String,
    val middlename: String? = null,
    val lastname: String,
    val age: Int,
    val occupations: List<String>,
    val countries: List<String>,
    val favouriteNumbers: List<Int>
)

class BusinessPerson(
    name: String,
    middlename: String? = null,
    lastname: String,
    age: Int,
    occupations: List<String>,
    countries: List<String>,
    favouriteNumbers: List<Int>,
    val businessName: String,
    val businessCard: FancyBusinessCard
) : Person(name, middlename, lastname, age, occupations, countries, favouriteNumbers)

open class BusinessCard(
    val email: String,
    val phone: String
)

class FancyBusinessCard(
    email: String,
    phone: String,
    val fax: String,
    val website: String
) : BusinessCard(email, phone)
