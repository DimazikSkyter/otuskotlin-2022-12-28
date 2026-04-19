package ru.escalop.common.calendar

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class YandexCalendar: ICalendarClient {

    override suspend fun readSnapshot(uuid: String): CalendarSnapshot? {
        return null //Request
    }

    override suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse {
        TODO("Not yet implemented")
    }
}