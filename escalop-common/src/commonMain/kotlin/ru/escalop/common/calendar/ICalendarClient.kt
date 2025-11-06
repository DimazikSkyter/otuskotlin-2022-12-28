package ru.escalop.common.calendar

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ICalendarClient {

    @OptIn(ExperimentalUuidApi::class)
    suspend fun readSnapshot(uuid: Uuid): CalendarSnapshot?

    suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse

    companion object {
        @ExperimentalUuidApi
        val NONE = object : ICalendarClient {
            override suspend fun readSnapshot(uuid: Uuid): CalendarSnapshot? {
                TODO("Not yet implemented")
            }

            override suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse {
                TODO("Not yet implemented")
            }
        }
    }
}