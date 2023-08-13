package net.nuclearprometheus.tpm.applicationserver.queries

import net.nuclearprometheus.tpm.applicationserver.queries.operations.OperationType
import org.junit.jupiter.api.Test

enum class SortDirection {
    ASC, DESC
}

data class Sort(val field: String, val direction: SortDirection)

data class Filter(val field: String, val operation: OperationType, val value: Any)

abstract class AbstractQuery<TEntity>(
    val page: Int? = null,
    val size: Int? = null,
    val sort: List<Sort> = emptyList(),
    val search: List<Filter> = emptyList()
)

class PersonQuery(
    page: Int? = null,
    size: Int? = null,
    sort: List<Sort> = emptyList(),
    search: List<Filter> = emptyList()
) : AbstractQuery<Person>(page, size, sort, search)

typealias FilterExecutor<TEntity> = (entity: TEntity, value: Any) -> Boolean

abstract class InMemoryQueryExecutor<TEntity> {

    protected abstract val querySorters: Map<String, Comparator<TEntity>>
    protected abstract val queryFilters: Map<String, Map<String, FilterExecutor<TEntity>>>

    private fun sortComparator(sort: List<Sort>): Comparator<TEntity> {
        if (sort.isEmpty()) {
            return Comparator { _, _ -> 0 }
        }

        return sort.map {
            val (field, direction) = it
            val sortComparator = querySorters[field] ?: throw IllegalArgumentException("Invalid sort expression: $field")
            if (direction == SortDirection.DESC) {
                sortComparator.reversed()
            } else {
                sortComparator
            }
        }.reduce { acc, comparator -> acc.thenComparing(comparator) }
    }

    private fun filterPredicate(search: List<Filter>): (TEntity) -> Boolean {
        return { person: TEntity ->
            search.all { filter ->
                val (field, operation, value) = filter
                val availablePredicates = queryFilters[field]
                    ?: throw IllegalArgumentException("Invalid filter expression: $field:$operation")
                val filterOperation = availablePredicates[operation.symbol]
                    ?: throw IllegalArgumentException("Invalid filter expression: $field:$operation")
                filterOperation(person, value)
            }
        }
    }

    fun execute(query: PersonQuery, items: List<TEntity>): List<TEntity> {
        return items.asSequence()
            .filter(filterPredicate(query.search))
            .sortedWith(sortComparator(query.sort))
            .drop((query.page ?: 0) * (query.size ?: 0))
            .take(query.size ?: Int.MAX_VALUE)
            .toList()
    }
}

class PersonQueryExecutor : InMemoryQueryExecutor<Person>() {

    override val querySorters = mapOf(
        "name" to Comparator { o1: Person, o2: Person -> o1.name.compareTo(o2.name, ignoreCase = true) },
        "middlename" to Comparator { o1: Person, o2: Person -> o1.middlename?.compareTo(o2.middlename ?: "", ignoreCase = true) ?: 0 },
        "lastname" to Comparator { o1: Person, o2: Person -> o1.lastname.compareTo(o2.lastname, ignoreCase = true) },
        "age" to Comparator { o1: Person, o2: Person -> o1.age.compareTo(o2.age) }
    )

    override val queryFilters = mapOf(
        "name" to mapOf(
            "eq" to { person: Person, value: Any -> person.name.equals(value as String, ignoreCase = true) },
            "contains" to { person: Person, value: Any -> person.name.contains(value as String, ignoreCase = true) },
            "null" to { person: Person, _: Any -> person.name == null },
            "empty" to { person: Person, _: Any -> person.name.isEmpty() },
        ),
        "middlename" to mapOf(
            "eq" to { person: Person, value: Any -> person.middlename?.equals(value as String, ignoreCase = true) ?: false },
            "contains" to { person: Person, value: Any -> person.middlename?.contains(value as String, ignoreCase = true) ?: false },
            "null" to { person: Person, _: Any -> person.middlename == null },
            "empty" to { person: Person, _: Any -> person.middlename?.isEmpty() ?: false },
        ),
        "lastname" to mapOf(
            "eq" to { person: Person, value: Any -> person.lastname.equals(value as String, ignoreCase = true) },
            "contains" to { person: Person, value: Any -> person.lastname.contains(value as String, ignoreCase = true) },
            "null" to { person: Person, _: Any -> person.lastname == null },
            "empty" to { person: Person, _: Any -> person.lastname.isEmpty() },
        ),
        "age" to mapOf(
            "eq" to { person: Person, value: Any -> person.age == value as Int },
            "gt" to { person: Person, value: Any -> person.age > value as Int },
            "gte" to { person: Person, value: Any -> person.age >= value as Int },
            "lt" to { person: Person, value: Any -> person.age < value as Int },
            "lte" to { person: Person, value: Any -> person.age <= value as Int },
            "null" to { person: Person, _: Any -> person.age == null },
        ),
        "occupations" to mapOf(
            "eq" to { person: Person, value: Any -> person.occupations.containsAll(value as List<*>) },
            "any" to { person: Person, value: Any -> person.occupations.any { (value as List<*>).contains(it) } },
            "all" to { person: Person, value: Any -> person.occupations.all { (value as List<*>).contains(it) } },
            "none" to { person: Person, value: Any -> person.occupations.none { (value as List<*>).contains(it) } },
            "null" to { person: Person, _: Any -> person.occupations == null },
        ),
    )
}



class QueryTest {

    @Test
    fun `should sort by name`() {
        val query = PersonQuery(sort = listOf(Sort("name", SortDirection.ASC)))
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Jane", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Jane", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
        ))
        assert(result[0].name == "Jane")
        assert(result[1].name == "Jane")
        assert(result[2].name == "John")
        assert(result[3].name == "John")
    }

    @Test
    fun `should sort by name desc`() {
        val query = PersonQuery(sort = listOf(Sort("name", SortDirection.DESC)))
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Jane", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Jane", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
        ))
        assert(result[0].name == "John")
        assert(result[1].name == "John")
        assert(result[2].name == "Jane")
        assert(result[3].name == "Jane")
    }

    @Test
    fun `should sort by name and age`() {
        val query = PersonQuery(sort = listOf(Sort("name", SortDirection.ASC), Sort("age", SortDirection.DESC)))
        val executor = PersonQueryExecutor()
        val result = executor.execute(query, listOf(
            Person("John", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("Jane", "Doe", "Smith", 30, listOf("Developer"), listOf(), listOf()),
            Person("John", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
            Person("Jane", "Doe", "Smith", 20, listOf("Developer"), listOf(), listOf()),
        ))
        assert(result[0].name == "Jane")
        assert(result[1].name == "Jane")
        assert(result[2].name == "John")
        assert(result[3].name == "John")
    }
}