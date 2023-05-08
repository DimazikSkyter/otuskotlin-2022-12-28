package ru.otus.homework2.model


/**
 * TreatmentDto - describes one of person treatment of his ill.
 * @param thread - shows a specific hit thread
 */
data class TreatmentDto(
    var header: String,
    var description: String,
    var thread: Int,
    var doctorId: Long
) {
    fun toJson(): String = ClientDto.gson.toJson(this)
}
