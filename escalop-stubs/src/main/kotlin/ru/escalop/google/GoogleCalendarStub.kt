package ru.escalop.google

import ru.escalop.common.calendar.*
import ru.escalop.common.model.SnapshotId
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