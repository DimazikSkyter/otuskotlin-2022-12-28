package ru.escalop.common.calendar

import kotlinx.datetime.LocalDate
import ru.escalop.common.model.DocumentType
import ru.escalop.common.model.Metric
import ru.escalop.common.model.Snapshot

data class CalendarSnapshot(
    val user: String,
    val date: LocalDate,
    val sourceDocumentName: String,
    val type: DocumentType,
    val name: String,
    val metrics: List<Metric>
) {

    fun toSnapshot(id: Long) = Snapshot(id, type, sourceDocumentName, date, metrics.map { it.name }, name)
}