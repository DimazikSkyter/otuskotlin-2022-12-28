package ru.escalop.common.converters

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ListToStringJsonConverter: AttributeConverter<List<String>, String> {

    companion object {
        val OBJECT_MAPPER = ObjectMapper()
    }

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        return attribute?.let { OBJECT_MAPPER.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return dbData?.let {
            OBJECT_MAPPER.readValue(it,
                OBJECT_MAPPER.typeFactory.constructCollectionType(List::class.java, String::class.java))
        } ?: emptyList()
    }
}