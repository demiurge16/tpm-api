package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*

class PersonQueryExecutor : InMemoryQueryExecutor<Person>() {

    override val querySorters = mapOf(
        "name" to Comparator { o1: Person, o2: Person -> o1.name.compareTo(o2.name, ignoreCase = true) },
        "middlename" to Comparator { o1: Person, o2: Person ->
            o1.middlename?.compareTo(o2.middlename ?: "", ignoreCase = true) ?: 0
        },
        "lastname" to Comparator { o1: Person, o2: Person -> o1.lastname.compareTo(o2.lastname, ignoreCase = true) },
        "age" to Comparator { o1: Person, o2: Person -> o1.age.compareTo(o2.age) }
    )

    override val queryFilters: Map<String, Map<String, FilterExecutor<Person>>> = mapOf(
        "name" to mapOf(
            "eq" to equal(Person::name),
            "contains" to contains(Person::name),
            "null" to isNull(Person::name),
            "empty" to isEmpty(Person::name)
        ),
        "middlename" to mapOf(
            "eq" to equal(Person::middlename),
            "contains" to contains(Person::middlename),
            "null" to isNull(Person::middlename),
            "empty" to isEmpty(Person::middlename),
        ),
        "lastname" to mapOf(
            "eq" to equal(Person::lastname),
            "contains" to contains(Person::lastname),
            "null" to isNull(Person::lastname),
            "empty" to isEmpty(Person::lastname),
        ),
        "age" to mapOf(
            "eq" to equal(Person::age),
            "gt" to greaterThan(Person::age),
            "gte" to greaterThanOrEqual(Person::age),
            "lt" to lessThan(Person::age),
            "lte" to lessThanOrEqual(Person::age),
            "null" to isNull(Person::age),
        ),
        "occupations" to mapOf(
            "eq" to equal(Person::occupations),
            "any" to any(Person::occupations),
            "all" to all(Person::occupations),
            "none" to none(Person::occupations),
            "null" to isNull(Person::occupations),
            "empty" to isEmpty(Person::occupations),
        ),
        "countries" to mapOf(
            "eq" to equal(Person::countries),
            "any" to any(Person::countries),
            "all" to all(Person::countries),
            "none" to none(Person::countries),
            "null" to isNull(Person::countries),
            "empty" to isEmpty(Person::countries),
        ),
        "favouriteNumbers" to mapOf(
            "eq" to equal(Person::favouriteNumbers),
            "any" to any(Person::favouriteNumbers),
            "all" to all(Person::favouriteNumbers),
            "none" to none(Person::favouriteNumbers),
            "null" to isNull(Person::favouriteNumbers),
            "empty" to isEmpty(Person::favouriteNumbers)
        ),
        "mood" to mapOf(
            "eq" to equal(Person::mood),
            "null" to isNull(Person::mood),
        )
    )
}

open class Person(
    val name: String,
    val middlename: String? = null,
    val lastname: String,
    val age: Int,
    val occupations: List<String>,
    val countries: List<String>,
    val favouriteNumbers: List<Int>,
    val mood: Mood = Mood.HAPPY
)

enum class Mood {
    HAPPY,
    SAD,
    ANGRY,
    CONFUSED,
    EXCITED,
    BORED,
    TIRED,
    SLEEPY,
    HUNGRY,
    THIRSTY,
    SICK,
    HEALTHY;
}