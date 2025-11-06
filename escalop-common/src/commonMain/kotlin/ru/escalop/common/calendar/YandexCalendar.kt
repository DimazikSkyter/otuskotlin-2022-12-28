package ru.escalop.common.calendar

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class YandexCalendar: ICalendarClient {

    @ExperimentalUuidApi
    override suspend fun readSnapshot(uuid: Uuid): CalendarSnapshot? {
        Request
    }

    override suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse {
        TODO("Not yet implemented")
    }
}