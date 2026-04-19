package ru.escalop.common.calendar

import kotlinx.serialization.json.JsonObject

interface IStorage {

    fun loadFile(client: StorageClientConnectionData): JsonObject

    fun replaceFile(client: StorageClientConnectionData, file: JsonObject)
}