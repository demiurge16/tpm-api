package net.nuclearprometheus.translationprojectmanager.queries.operations

interface ComparisonOperation<TEntity : Any> : Operation {
    val field: String

    fun evaluate(entity: TEntity): Boolean

    fun getFieldValue(entity: TEntity): Any? {
        val fieldPath = field.split(".")
        var currentObject: Any? = entity

        fieldPath.forEach { fieldName ->
            val field = currentObject!!::class.java.getDeclaredField(fieldName)
            field.isAccessible = true
            currentObject = field.get(currentObject)
        }

        return currentObject
    }
}
