package ru.otus.common.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Snapshot(
    val id: Long, val type: String, val date: LocalDate, val metrics: Map<String, Double>, val name: String
)