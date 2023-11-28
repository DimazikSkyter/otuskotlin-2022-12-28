package ru.otus.common.calendar

import ru.otus.common.repo.*

interface ICalendarClient {

    suspend fun readSnapshot(id: Long): CalendarSnapshot?

    suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse

    companion object {
        val NONE = object : ICalendarClient {
            override suspend fun readSnapshot(id: Long): CalendarSnapshot {
                TODO("Not yet implemented")
            }

            override suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse {
                TODO("Not yet implemented")
            }
        }
    }
}