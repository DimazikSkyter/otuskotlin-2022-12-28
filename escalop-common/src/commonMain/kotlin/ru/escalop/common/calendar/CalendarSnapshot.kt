package ru.escalop.common.calendar

import kotlinx.datetime.LocalDate
import ru.escalop.common.entity.Metric
import ru.escalop.common.entity.Snapshot
import ru.escalop.common.model.DocumentType

data class CalendarSnapshot(
    val user: String,
    val date: LocalDate,
    val type: DocumentType,
    val name: String,
    val metrics: List<Metric>
) {

    fun toSnapshot(id: Long) = Snapshot(id, type, date, metrics.map { it.name }, name)
}