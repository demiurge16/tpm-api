package net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.specifications

sealed class Specification<TEntity : Any> {
    class AndSpecification<TEntity : Any>(val left: Specification<TEntity>, val right: Specification<TEntity>) : Specification<TEntity>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is AndSpecification<*>) return false

            if (left != other.left) return false
            if (right != other.right) return false

            return true
        }

        override fun hashCode(): Int {
            var result = left.hashCode()
            result = 31 * result + right.hashCode()
            return result
        }
    }
    class OrSpecification<TEntity : Any>(val left: Specification<TEntity>, val right: Specification<TEntity>) : Specification<TEntity>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is OrSpecification<*>) return false

            if (left != other.left) return false
            if (right != other.right) return false

            return true
        }

        override fun hashCode(): Int {
            var result = left.hashCode()
            result = 31 * result + right.hashCode()
            return result
        }
    }

    class NotSpecification<TEntity : Any>(val specification: Specification<TEntity>) : Specification<TEntity>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is NotSpecification<*>) return false

            if (specification != other.specification) return false

            return true
        }

        override fun hashCode(): Int {
            return specification.hashCode()
        }
    }

    class TrueSpecification<TEntity : Any> : Specification<TEntity>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is TrueSpecification<*>) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class FalseSpecification<TEntity : Any> : Specification<TEntity>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is FalseSpecification<*>) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    abstract class UnarySpecification<TEntity : Any>(val name: String) : Specification<TEntity>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is UnarySpecification<*>) return false

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }
    abstract class BinarySpecification<TEntity : Any, TValue : Any>(val name: String, val value: TValue?) : Specification<TEntity>() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is BinarySpecification<*, *>) return false

            if (name != other.name) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + (value?.hashCode() ?: 0)
            return result
        }
    }
}

