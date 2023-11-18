package net.nuclearprometheus.tpm.applicationserver.domain.queries.search.dsl

import net.nuclearprometheus.tpm.applicationserver.domain.queries.executors.ValueGetter
import net.nuclearprometheus.tpm.applicationserver.domain.queries.search.operations.*
import kotlin.reflect.KClass

class SearchSpecificationBuilder<TEntity : Any> {
    private val definitions: MutableMap<String, Definition<TEntity>> = mutableMapOf()

    fun <TValue : Any> uniqueToken(name: String, valueClass: KClass<TValue>)= UniqueTokenDefinitionBuilder(name, valueClass)
    inner class UniqueTokenDefinitionBuilder<TValue : Any>(private val name: String, private val valueClass: KClass<TValue>) {
        infix fun using(getter: ValueGetter<TEntity, TValue?>) {
            definitions[name] = UniqueToken(getter, valueClass)
        }
    }

    fun string(name: String) = StringDefinitionBuilder(name)
    inner class StringDefinitionBuilder(private val name: String) {
        infix fun using(getter: ValueGetter<TEntity, String?>) {
            definitions[name] = StringToken(getter)
        }
    }

    fun <TValue : Comparable<TValue>> comparable(name: String, valueClass: KClass<TValue>) = ComparableDefinitionBuilder(name, valueClass)
    inner class ComparableDefinitionBuilder<TValue : Comparable<TValue>>(private val name: String, private val valueClass: KClass<TValue>) {
        infix fun using(getter: ValueGetter<TEntity, TValue?>) {
            definitions[name] = ComparableToken(getter, valueClass)
        }
    }

    fun boolean(name: String) =  BooleanDefinitionBuilder(name)
    inner class BooleanDefinitionBuilder(private val name: String) {
        infix fun using(getter: ValueGetter<TEntity, Boolean?>) {
            definitions[name] = BooleanToken(getter)
        }
    }

    fun <TValue : Any> collection(name: String, valueClass: KClass<TValue>) = CollectionDefinitionBuilder(name, valueClass)
    inner class CollectionDefinitionBuilder<TValue : Any>(private val name: String, private val valueClass: KClass<TValue>) {
        infix fun using(getter: ValueGetter<TEntity, Collection<TValue>?>) {
            definitions[name] = CollectionToken(getter, valueClass)
        }
    }

    fun <TValue : Enum<TValue>> enum(name: String, valueClass: KClass<TValue>) = EnumDefinitionBuilder(name, valueClass)
    inner class EnumDefinitionBuilder<TValue : Enum<TValue>>(private val name: String, private val valueClass: KClass<TValue>) {
        infix fun using(getter: ValueGetter<TEntity, TValue?>) {
            definitions[name] = EnumToken(getter, valueClass)
        }
    }

    fun build(): SearchSpecification<TEntity> {
        return SearchSpecification(definitions)
    }
}
