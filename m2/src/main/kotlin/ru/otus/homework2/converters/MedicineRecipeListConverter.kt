package ru.otus.homework2.converters

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import ru.otus.homework2.model.MedicineRecipe

@Converter(autoApply = true)
class MedicineRecipeListConverter : AttributeConverter<List<MedicineRecipe>, String> {

    override fun convertToDatabaseColumn(attribute: List<MedicineRecipe>?): String {
        TODO("Not yet implemented")
    }

    override fun convertToEntityAttribute(dbData: String?): List<MedicineRecipe> {
        TODO("Not yet implemented")
    }

    companion object {
        val objectMapper: ObjectMapper = ObjectMapper()
    }

}
