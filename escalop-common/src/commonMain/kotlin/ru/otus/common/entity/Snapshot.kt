package ru.otus.common.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import ru.otus.common.model.DocumentType

@Serializable
data class Snapshot(
    val id: Long, val type: DocumentType, val date: LocalDate, val metrics: List<String>, val name: String
)