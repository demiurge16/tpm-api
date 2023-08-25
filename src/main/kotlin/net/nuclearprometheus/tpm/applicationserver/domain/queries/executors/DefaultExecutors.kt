package net.nuclearprometheus.tpm.applicationserver.domain.queries.executors

inline fun <TEntity : Any, TValue : Any> equal(
    crossinline valueGetter: ValueGetter<TEntity, TValue?>
): FilterExecutor<TEntity> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte -> fieldValue.toString().toByte() == value.toString().toByte()
            is Short -> valueGetter(entity) == value.toString().toShort()
            is Int -> valueGetter(entity) == value.toString().toInt()
            is Long -> valueGetter(entity) == value.toString().toLong()
            is Float -> valueGetter(entity) == value.toString().toFloat()
            is Double -> valueGetter(entity) == value.toString().toDouble()
            is String -> valueGetter(entity) == value.toString()
            is Enum<*> -> (valueGetter(entity) as Enum<*>?)?.let { it == (value as Enum<*>) } ?: false
            is Collection<*> -> if (value is Collection<*>) {
                (valueGetter(entity) as Collection<*>?)?.containsAll(value) ?: false
            } else {
                (valueGetter(entity) as Collection<*>?)?.contains(value) ?: false
            }
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

inline fun <TEntity : Any, TValue : Any> contains(
    crossinline valueGetter: ValueGetter<TEntity, TValue?>
): FilterExecutor<TEntity> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is String -> fieldValue.contains(value.toString(), ignoreCase = true)
            is Enum<*> -> (valueGetter(entity) as Enum<*>?)?.let { it == (value as Enum<*>) } ?: false
            is Collection<*> -> if (value is Collection<*>) {
                (valueGetter(entity) as Collection<*>?)?.containsAll(value) ?: false
            } else {
                (valueGetter(entity) as Collection<*>?)?.contains(value) ?: false
            }
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }

    }
}

inline fun <TEntity, TValue> greaterThan(
    crossinline valueGetter: ValueGetter<TEntity, TValue?>
): FilterExecutor<TEntity> where TEntity : Any, TValue : Any, TValue : Comparable<TValue> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte -> (valueGetter(entity) as Byte?)?.let { it > value.toString().toByte() } ?: false
            is Short -> (valueGetter(entity) as Short?)?.let { it > value.toString().toShort() } ?: false
            is Int -> (valueGetter(entity) as Int?)?.let { it > value.toString().toInt() } ?: false
            is Long -> (valueGetter(entity) as Long?)?.let { it > value.toString().toLong() } ?: false
            is Float -> (valueGetter(entity) as Float?)?.let { it > value.toString().toFloat() } ?: false
            is Double -> (valueGetter(entity) as Double?)?.let { it > value.toString().toDouble() } ?: false
            is String -> (valueGetter(entity) as String?)?.let { it > value.toString() } ?: false
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

inline fun <TEntity, TValue> lessThan(
    crossinline valueGetter: ValueGetter<TEntity, TValue?>
): FilterExecutor<TEntity> where TEntity : Any, TValue : Any, TValue : Comparable<TValue> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte -> (valueGetter(entity) as Byte?)?.let { it < value.toString().toByte() } ?: false
            is Short -> (valueGetter(entity) as Short?)?.let { it < value.toString().toShort() } ?: false
            is Int -> (valueGetter(entity) as Int?)?.let { it < value.toString().toInt() } ?: false
            is Long -> (valueGetter(entity) as Long?)?.let { it < value.toString().toLong() } ?: false
            is Float -> (valueGetter(entity) as Float?)?.let { it < value.toString().toFloat() } ?: false
            is Double -> (valueGetter(entity) as Double?)?.let { it < value.toString().toDouble() } ?: false
            is String -> (valueGetter(entity) as String?)?.let { it < value.toString() } ?: false
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

inline fun <TEntity, TValue> greaterThanOrEqual(
    crossinline valueGetter: ValueGetter<TEntity, TValue?>
): FilterExecutor<TEntity> where TEntity : Any, TValue : Any, TValue : Comparable<TValue> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte -> (valueGetter(entity) as Byte?)?.let { it >= value.toString().toByte() } ?: false
            is Short -> (valueGetter(entity) as Short?)?.let { it >= value.toString().toShort() } ?: false
            is Int -> (valueGetter(entity) as Int?)?.let { it >= value.toString().toInt() } ?: false
            is Long -> (valueGetter(entity) as Long?)?.let { it >= value.toString().toLong() } ?: false
            is Float -> (valueGetter(entity) as Float?)?.let { it >= value.toString().toFloat() } ?: false
            is Double -> (valueGetter(entity) as Double?)?.let { it >= value.toString().toDouble() } ?: false
            is String -> (valueGetter(entity) as String?)?.let { it >= value.toString() } ?: false
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

inline fun <TEntity, TValue> lessThanOrEqual(
    crossinline valueGetter: ValueGetter<TEntity, TValue?>
): FilterExecutor<TEntity> where TEntity : Any, TValue : Any, TValue : Comparable<TValue> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte -> (valueGetter(entity) as Byte?)?.let { it <= value.toString().toByte() } ?: false
            is Short -> (valueGetter(entity) as Short?)?.let { it <= value.toString().toShort() } ?: false
            is Int -> (valueGetter(entity) as Int?)?.let { it <= value.toString().toInt() } ?: false
            is Long -> (valueGetter(entity) as Long?)?.let { it <= value.toString().toLong() } ?: false
            is Float -> (valueGetter(entity) as Float?)?.let { it <= value.toString().toFloat() } ?: false
            is Double -> (valueGetter(entity) as Double?)?.let { it <= value.toString().toDouble() } ?: false
            is String -> (valueGetter(entity) as String?)?.let { it <= value.toString() } ?: false
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

fun <TEntity : Any, TValue : Any> all(valueGetter: ValueGetter<TEntity, TValue?>): FilterExecutor<TEntity> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte, is Short, is Int, is Long, is Float, is Double, is String, is Enum<*> -> when (value) {
                is Collection<*> -> value.all { it == valueGetter(entity) }
                else -> value == valueGetter(entity)
            }
            is Collection<*> -> when (value) {
                is Collection<*> -> value.all { it in (valueGetter(entity) as Collection<*>) }
                else -> value in (valueGetter(entity) as Collection<*>)
            }
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

fun <TEntity : Any, TValue : Any> any(valueGetter: ValueGetter<TEntity, TValue?>): FilterExecutor<TEntity> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte, is Short, is Int, is Long, is Float, is Double, is String, is Enum<*> -> when (value) {
                is Collection<*> -> value.any { it == valueGetter(entity) }
                else -> value == valueGetter(entity)
            }
            is Collection<*> -> when (value) {
                is Collection<*> -> value.any { it in (valueGetter(entity) as Collection<*>) }
                else -> value in (valueGetter(entity) as Collection<*>)
            }
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

fun <TEntity : Any, TValue : Any> none(valueGetter: ValueGetter<TEntity, TValue?>): FilterExecutor<TEntity> {
    return { entity: TEntity, value: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is Byte, is Short, is Int, is Long, is Float, is Double, is String, is Enum<*> -> when (value) {
                is Collection<*> -> value.none { it == valueGetter(entity) }
                else -> value != valueGetter(entity)
            }
            is Collection<*> -> when (value) {
                is Collection<*> -> value.none { it in (valueGetter(entity) as Collection<*>) }
                else -> value !in (valueGetter(entity) as Collection<*>)
            }
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}

fun <TEntity : Any> isNull(valueGetter: ValueGetter<TEntity, Any?>): FilterExecutor<TEntity> {
    return { entity: TEntity, _: Any -> valueGetter(entity) == null }
}

fun <TEntity : Any, TValue : Any> isEmpty(valueGetter: ValueGetter<TEntity, TValue?>): FilterExecutor<TEntity> {
    return { entity: TEntity, _: Any ->
        when (val fieldValue = valueGetter(entity)) {
            is String -> (valueGetter(entity) as String?)?.isEmpty() ?: true
            is Collection<*> -> (valueGetter(entity) as Collection<*>).isEmpty()
            else -> throw IllegalArgumentException("Type ${fieldValue?.let { it::class }} is not supported")
        }
    }
}
