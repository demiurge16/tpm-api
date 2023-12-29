package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.*

object PersonQueryExecutor : QueryExecutor<Person>() {

    override val querySorters = sortExecutors<Person> {
        sort("name", compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
        sort("middlename", compareBy(nullsLast(String.CASE_INSENSITIVE_ORDER)) { it.middlename })
        sort("lastname", compareBy(String.CASE_INSENSITIVE_ORDER) { it.lastname })
        sort("age", compareBy { it.age })
    }

    override val specificationExecutors = specificationExecutors {
        string("name", Person::name)
        string("middlename", Person::middlename)
        string("lastname", Person::lastname)
        comparable("age", Person::age)
        collection("occupations", Person::occupations)
        collection("countries", Person::countries)
        collection("favouriteNumbers", Person::favouriteNumbers)
        enum("mood", Person::mood)
    }
}