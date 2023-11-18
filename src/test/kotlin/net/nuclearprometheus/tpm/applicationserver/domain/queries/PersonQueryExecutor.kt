package net.nuclearprometheus.tpm.applicationserver.domain.queries

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.InMemoryQueryExecutor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl.searchSpecification
import net.nuclearprometheus.tpm.applicationserver.domain.queries.sort.dsl.sortSpecification
import java.util.*
import kotlin.Comparator

object PersonQueryExecutor : InMemoryQueryExecutor<Person>() {
    override val querySorters = sortSpecification {
        sorter("name") using Comparator { o1: Person, o2: Person -> o1.name.compareTo(o2.name, ignoreCase = true) }
        sorter("middlename") using Comparator { o1: Person, o2: Person ->
            o1.middlename?.compareTo(o2.middlename ?: "", ignoreCase = true) ?: 0
        }
        sorter("lastname") using Comparator { o1: Person, o2: Person ->
            o1.lastname.compareTo(o2.lastname, ignoreCase = true)
        }
        sorter("age") using Comparator { o1: Person, o2: Person -> o1.age.compareTo(o2.age) }
    }

    override val searchSpecification = searchSpecification {
        uniqueToken("id", UUID::class) using Person::id
        string("name") using Person::name
        string("middlename") using Person::middlename
        string("lastname") using Person::lastname
        comparable("age", Int::class) using Person::age
        collection("occupations", String::class) using Person::occupations
        collection("countries", String::class) using Person::countries
        collection("favouriteNumbers", Int::class) using Person::favouriteNumbers
        enum("mood", Mood::class) using Person::mood
    }
}