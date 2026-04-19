package ru.escalop.common.calendar

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ICalendarClient {

    suspend fun readSnapshot(uuid: String): CalendarSnapshot?

    suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse

    companion object {
        val NONE = object : ICalendarClient {
            override suspend fun readSnapshot(uuid: String): CalendarSnapshot? {
                TODO("Not yet implemented")
            }

            override suspend fun writeSnapshot(writeRequest: WriteRequest): WriteResponse {
                TODO("Not yet implemented")
            }
        }
    }
}