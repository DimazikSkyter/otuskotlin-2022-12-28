package ru.otus.google

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import ru.otus.common.calendar.*
import ru.otus.common.entity.Metric
import ru.otus.common.model.SnapshotId
import java.util.concurrent.atomic.AtomicLong

class GoogleCalendarStub(
    private val cache: MutableMap<Long, CalendarSnapshot> = mutableMapOf()
): ICalendarClient {
    private val idSupplier: AtomicLong = AtomicLong(0)

    override suspend fun readSnapshot(id: Long): CalendarSnapshot? {
        return cache[id]
    }

    override suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse {
        return try {
            val id = idSupplier.getAndIncrement()
            cache[id] = writeRequest.calendarSnapshot
            WriteResponse(SnapshotId(id), CalendarResponseStatus.SUCCESS)
        } catch (e: Exception) {
            WriteResponse(null, CalendarResponseStatus.FAILED)
        }
    }
}