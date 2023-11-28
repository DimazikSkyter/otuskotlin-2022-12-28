package ru.otus.api.v1.models

import com.fasterxml.jackson.module.kotlin.readValue
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


//todo переписать
class TransportModelTest {

    private val searchString = "%sasda%"
    private val readRequestId = "123"
    private val searchRequestId = "124"
    private val uploadRequestId = "122"
    private val uploadResponseId = "125"
    private val readResponseId = "126"
    private val searchResponseId = "127"
    private val snapshotId = BigDecimal.valueOf(3)
    private val documentDate = "2023.09.16"
    private val documentName = "documentName"
    private val documentType = "blood general"

    private val userId = "some user id"

    private val jsonKey = "some json key"
    private val jsonValue = "some json value"
    private val snapshotJson = "{\"$jsonKey\":\"$jsonValue\"}"

    private val id = BigDecimal.valueOf(33)
    private val snapshotMetaDateObject: SnapshotMetaDataObject = SnapshotMetaDataObject(id)

    private val snapshotListObjectId = BigDecimal.valueOf(56)
    private val type = DocumentType.GENERAL
    private val date = "20.03.1999"
    private val nameSnapshotListObject = "snapshot name"
    private val uploadMessage = "Document upload response"

    private val snapshotListObject: SnapshotListObject = SnapshotListObject(
        id = snapshotListObjectId,
        type = type,
        date = date,
        name = nameSnapshotListObject
    )

    private val snapshotReadRequest: SnapshotReadRequest = SnapshotReadRequest(
        requestId = readRequestId,
        debug = SnapshotDebug(SnapshotRequestDebugMode.TEST,
            SnapshotRequestDebugStubs.SUCCESS),
        snapshot = SnapshotReadObject(snapshotId)
    )

     private val snapshotSearchRequest: SnapshotSearchRequest = SnapshotSearchRequest(
         requestId = searchRequestId,
         debug = SnapshotDebug(SnapshotRequestDebugMode.STUB,
             SnapshotRequestDebugStubs.BAD_TITLE),
         filter = SnapshotSearchFilter("%sasda%")
     )

    private val uploadResponse: DocumentUploadResponse = DocumentUploadResponse(
        requestId = uploadResponseId,
        result = ResponseResult.SUCCESS,
        message = uploadMessage
    )

    private val snapshotReadResponse: SnapshotReadResponse = SnapshotReadResponse(
        requestId = readResponseId,
        result = ResponseResult.SUCCESS,
        metadata = snapshotMetaDateObject,
        snapshot = snapshotJson,
        permissions = emptySet()
    )

    private val snapshotSearchResponse: SnapshotSearchResponse = SnapshotSearchResponse(
        requestId = searchResponseId,
        userId = userId,
        result = ResponseResult.ERROR,
        list = listOf(snapshotListObject)
    )

    private val documentUploadRequest: DocumentUploadRequest = DocumentUploadRequest(
        requestId = uploadRequestId,
        debug = SnapshotDebug(SnapshotRequestDebugMode.STUB,
            SnapshotRequestDebugStubs.SUCCESS),
        userId = userId,
        uploadMetaData = DocumentUploadObject("document name", DocumentType.GENERAL, "some file in base64")
    )

    @Test
    fun `should serialize readRequest` () {
        val json = apiV1Mapper.writeValueAsString(snapshotReadRequest)

        assertContains(json, "stub\":\"success")
        assertContains(json, "requestType\":\"read")
        assertContains(json, "mode\":\"${SnapshotRequestDebugMode.TEST.value}")
        assertContains(json, "requestId\":\"$readRequestId")
        assertContains(json, "snapshot\":{\"id\":$snapshotId")
    }

    @Test
    fun `should deserialized readRequest`() {
        val json = apiV1Mapper.writeValueAsString(snapshotReadRequest)
        val obj = apiV1Mapper.readValue<SnapshotReadRequest>(json)

        assertEquals(snapshotReadRequest, obj)
    }

    @Test
    fun `should serialize searchRequest`() {
        val json = apiV1Mapper.writeValueAsString(snapshotSearchRequest)

        assertContains(json, "requestType\":\"search")
        assertContains(json, "stub\":\"badTitle")
        assertContains(json, "mode\":\"${SnapshotRequestDebugMode.STUB.value}")
        assertContains(json, "requestId\":\"$searchRequestId")
        assertContains(json, "filter\":{\"searchString\":\"$searchString")
    }

    @Test
    fun `should deserialized searchRequest`() {
        val json = apiV1Mapper.writeValueAsString(snapshotSearchRequest)
        val obj = apiV1Mapper.readValue<SnapshotSearchRequest>(json)

        assertEquals(snapshotSearchRequest, obj)
    }

    @Test
    fun `should serialize documentUploadRequest`() {
        val json = apiV1Mapper.writeValueAsString(documentUploadRequest)

        println(json)

        assertContains(json, "mode\":\"${SnapshotRequestDebugMode.STUB.value}")
        assertContains(json, "stub\":\"${SnapshotRequestDebugStubs.SUCCESS.value}")
        assertContains(json, "requestId\":\"$uploadRequestId")
        assertContains(json, "userId\":\"$userId")
        assertContains(json, "file\":\"some file in base64")
    }
    @Test
    fun `should deserialize documentUploadRequest`() {
        val json = apiV1Mapper.writeValueAsString(documentUploadRequest)
        val obj = apiV1Mapper.readValue<DocumentUploadRequest>(json)

        assertEquals(documentUploadRequest, obj)
    }

    @Test
    fun `should serialize uploadResponse`() {
        val json = apiV1Mapper.writeValueAsString(uploadResponse)

        assertContains(json, "responseType\":\"upload")
        assertContains(json, "errors\":null")
        assertContains(json, "message\":\"$uploadMessage")
    }

    @Test
    fun `should deserialized uploadResponse`() {
        val json = apiV1Mapper.writeValueAsString(uploadResponse)
        val obj = apiV1Mapper.readValue<DocumentUploadResponse>(json)

        assertEquals(uploadResponse, obj)
    }

    @Test
    fun `should serialize snapshotReadResponse`() {
        val json = apiV1Mapper.writeValueAsString(snapshotReadResponse)

        assertContains(json, "responseType\":\"read")
        assertContains(json, "requestId\":\"$readResponseId")
        assertContains(json, "result\":\"${ResponseResult.SUCCESS.value}")
        assertContains(json, "errors\":null")
        assertContains(json, "some json key")
        assertContains(json, "some json value")
    }

    @Test
    fun `should deserialized snapshotReadResponse`() {
        val json = apiV1Mapper.writeValueAsString(snapshotReadResponse)
        val obj = apiV1Mapper.readValue<SnapshotReadResponse>(json)

        assertEquals(snapshotReadResponse, obj)
    }

    @Test
    fun `should serialize snapshotSearchResponse`() {
        val json = apiV1Mapper.writeValueAsString(snapshotSearchResponse)

        println(json)

        assertContains(json, "responseType\":\"search")
        assertContains(json, "requestId\":\"$searchResponseId")
        assertContains(json, "result\":\"${ResponseResult.ERROR.value}")
        assertContains(json, "errors\":null")
        assertContains(json, "id\":$snapshotListObjectId")
        assertContains(json, "userId\":\"$userId")
        assertContains(json, "date\":\"$date")
        assertContains(json, "name\":\"$nameSnapshotListObject")
    }
}