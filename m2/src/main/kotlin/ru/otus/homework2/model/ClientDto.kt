package ru.otus.homework2.model

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ClientDto(
    val fio: String?,
    val birthDate: LocalDate?,
    val sexMale: Boolean?
) {

    fun toJson(): String = gson.toJson(this)

    companion object {
        val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
    }

    internal class LocalDateAdapter : JsonSerializer<LocalDate> {
        override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(src!!.format(DateTimeFormatter.ISO_DATE))
        }
    }
}
