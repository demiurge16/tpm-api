package net.nuclearprometheus.tpm.applicationserver.queries.operations

import java.lang.reflect.Field

interface ComparisonOperation<TEntity : Any> : Operation<TEntity> {
    val field: String

    fun evaluate(entity: TEntity): Boolean

    fun getFieldValue(entity: TEntity): Any? {
        val fieldPath = field.split(".")
        var currentObject: Any? = entity

        var fields = getAllFields(currentObject!!::class.java)

        fieldPath.forEach { fieldName ->
            val field = fields.firstOrNull { it.name == fieldName }
                ?: throw IllegalArgumentException("Field $fieldName not found in class ${currentObject!!::class.java.name}")

            field.isAccessible = true
            currentObject = field.get(currentObject)

            if (currentObject == null) {
                return null
            }

            fields = getAllFields(currentObject!!::class.java)
        }

        return currentObject
    }

    fun getAllFields(clazz: Class<*>): List<Field> {
        val fields = mutableListOf<Field>()
        fields.addAll(clazz.declaredFields)
        if (clazz.superclass != null) {
            fields.addAll(getAllFields(clazz.superclass))
        }
        return fields
    }
}
