package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*

object PersonQueryExecutor : QueryExecutor<Person>() {

    override val querySorters = mapOf(
        "name" to Comparator { o1: Person, o2: Person -> o1.name.compareTo(o2.name, ignoreCase = true) },
        "middlename" to Comparator { o1: Person, o2: Person ->
            o1.middlename?.compareTo(o2.middlename ?: "", ignoreCase = true) ?: 0
        },
        "lastname" to Comparator { o1: Person, o2: Person -> o1.lastname.compareTo(o2.lastname, ignoreCase = true) },
        "age" to Comparator { o1: Person, o2: Person -> o1.age.compareTo(o2.age) }
    )

    override val specificationExecutors: Map<String, SpecificationExecutor<Person, *>> = mapOf(
        string("name", Person::name),
        string("middlename", Person::middlename),
        string("lastname", Person::lastname),
        comparable("age", Person::age),
        collection("occupations", Person::occupations),
        collection("countries", Person::countries),
        collection("favouriteNumbers", Person::favouriteNumbers),
        enum("mood", Person::mood)
    )
}