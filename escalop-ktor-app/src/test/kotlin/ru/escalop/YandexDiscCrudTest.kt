package ru.escalop

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import java.nio.charset.StandardCharsets
import java.time.Instant
import kotlinx.coroutines.runBlocking

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class YandexDiskCrudTest {

    private val token: String? = System.getenv("YANDEX_DISK_TOKEN")
        ?: System.getProperty("yandex.disk.token") ?: "y0__xC4_IEfGI65OyCzuo6JFTDQpPizCDLgofWKZTUH62OCE9w6JoGAhHXL"

    private val client = HttpClient(CIO) { expectSuccess = false }

    // ВЫБЕРИ базовый путь:
    // 1) Если у тебя скоупы disk.read/disk.write: оставь "/test"
    // 2) Если используешь скоуп cloud_api:disk.app_folder: поставь "app:/test"
    private val basePath = "/test"

    // Уникальный путь к файлу
    private val remotePath = "$basePath/otuskotlin-1762604387.txt"

    @BeforeEach
    fun requireToken() {
        assumeTrue(!token.isNullOrBlank()) {
            "YANDEX_DISK_TOKEN не задан. Укажи переменную окружения или -Dyandex.disk.token=..."
        }
    }

    @Test
    @Order(1)
    fun createOrUploadFile() = runBlocking {
        // гарантируем, что директория существует
        ensureDirectoryExists(basePath)

        // 1) Получить upload URL
        val href = getUploadHref(remotePath, overwrite = true)

        // 2) Загрузить содержимое
        val content = "Hello from Kotlin test at ${Instant.now()}".toByteArray(StandardCharsets.UTF_8)
        val putResp = client.put(href) { setBody(content) }
        val putBody = putResp.bodyAsText()
        assertTrue(putResp.status.isSuccess(), "Upload failed: ${putResp.status} $putBody")

        // 3) Проверить наличие ресурса (метаданные)
        val metaResp = client.get("https://cloud-api.yandex.net/v1/disk/resources") {
            header(HttpHeaders.Authorization, "OAuth $token")
            parameter("path", remotePath)
        }
        val metaBody = metaResp.bodyAsText()
        assertTrue(metaResp.status.isSuccess(), "Meta check failed: ${metaResp.status} $metaBody")
        assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), metaResp.contentType())
    }

    @Test
    @Order(2)
    fun downloadAndPrintFile() = runBlocking {
        // 1) Получить download URL
        val href = getDownloadHref(remotePath)

        // 2) Скачать и вывести
        val getResp = client.get(href)
        val body = getResp.bodyAsText()
        assertTrue(getResp.status.isSuccess(), "Download failed: ${getResp.status} $body")

        println("----- FILE CONTENT BEGIN -----")
        println(body)
        println("----- FILE CONTENT END   -----")

        assertTrue(body.isNotBlank(), "Файл пустой?")
    }

    @Test @Order(3)
    fun appendLineAndOverwriteFile() = runBlocking {
        // 1) скачать текущий контент
        val downloadHref = getDownloadHref(remotePath)
        val currentResp = client.get(downloadHref)
        val currentBody = currentResp.bodyAsText()
        assertTrue(currentResp.status.isSuccess(), "Initial download failed: ${currentResp.status} $currentBody")

        // 2) сформировать новую версию (append)
        val appendedLine = "Appended line at ${Instant.now()}"
        val newContent = buildString {
            append(currentBody)
            if (!currentBody.endsWith("\n")) append("\n")
            append(appendedLine)
            append("\n")
        }.toByteArray(StandardCharsets.UTF_8)

        // 3) получить upload href c overwrite=true и перезалить файл
        val uploadHref = getUploadHref(remotePath, overwrite = true)
        val putResp = client.put(uploadHref) { setBody(newContent) }
        val putBody = putResp.bodyAsText()
        assertTrue(putResp.status.isSuccess(), "Overwrite failed: ${putResp.status} $putBody")

        // 4) проверить, что строка действительно добавилась
        val verifyHref = getDownloadHref(remotePath)
        val verifyResp = client.get(verifyHref)
        val verifyBody = verifyResp.bodyAsText()
        assertTrue(verifyResp.status.isSuccess(), "Verify download failed: ${verifyResp.status} $verifyBody")
        assertTrue(verifyBody.contains(appendedLine), "Не нашли добавленную строку в файле")
    }

    // ---------- helpers ----------

    /** Создаёт директорию, если её нет.
     *  Работает и с обычным путём (/test), и с app:/test (app_folder). */
    private suspend fun ensureDirectoryExists(dirPath: String) {
        // Проверяем метаданные
        val meta = client.get("https://cloud-api.yandex.net/v1/disk/resources") {
            header(HttpHeaders.Authorization, "OAuth $token")
            parameter("path", dirPath)
        }
        when (meta.status.value) {
            200 -> return // уже есть
            404 -> {
                // создаём папку
                val create = client.put("https://cloud-api.yandex.net/v1/disk/resources") {
                    header(HttpHeaders.Authorization, "OAuth $token")
                    parameter("path", dirPath)
                }
                val text = create.bodyAsText()
                // 201 Created — ок; 409 AlreadyExists — тоже ок (гонка)
                assertTrue(
                    create.status == HttpStatusCode.Created || create.status == HttpStatusCode.Conflict,
                    "Create dir failed: ${create.status} $text"
                )
            }
            else -> {
                val text = meta.bodyAsText()
                assertTrue(false, "Dir meta failed: ${meta.status} $text")
            }
        }
    }

    private suspend fun getUploadHref(path: String, overwrite: Boolean): String {
        val resp = client.get("https://cloud-api.yandex.net/v1/disk/resources/upload") {
            header(HttpHeaders.Authorization, "OAuth $token")
            parameter("path", path)
            parameter("overwrite", overwrite)
        }
        val text = resp.bodyAsText()
        assertTrue(resp.status.isSuccess(), "Get upload href failed: ${resp.status} $text")
        val href = Json.parseToJsonElement(text).jsonObject["href"]?.jsonPrimitive?.content
        require(!href.isNullOrBlank()) { "upload href not found in response" }
        return href
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