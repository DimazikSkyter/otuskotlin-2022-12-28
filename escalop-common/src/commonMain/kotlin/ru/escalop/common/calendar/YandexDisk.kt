package ru.escalop.common.calendar

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class YandexDisk (
    client: HttpClient
) : IStorage {
    override fun loadFile(client: StorageClientConnectionData): JsonObject {
        TODO("Not yet implemented")
    }

    override fun replaceFile(client: StorageClientConnectionData, file: JsonObject) {
        TODO("Not yet implemented")
    }

    private suspend fun getDownloadHref(path: String): String {
        val resp = client.get("https://cloud-api.yandex.net/v1/disk/resources/download") {
            header(HttpHeaders.Authorization, "OAuth $token")
            parameter("path", path)
        }
        val text = resp.bodyAsText()
        assertTrue(resp.status.isSuccess(), "Get download href failed: ${resp.status} $text")
        val href = Json.parseToJsonElement(text).jsonObject["href"]?.jsonPrimitive?.content
        require(!href.isNullOrBlank()) { "download href not found in response" }
        return href
    }
}