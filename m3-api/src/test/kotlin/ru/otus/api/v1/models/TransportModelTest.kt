package ru.otus.api.v1.models

import com.fasterxml.jackson.module.kotlin.readValue
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class TransportModelTest {

    private val searchString = "%sasda%"
    private val readRequestId = "123"
    private val searchRequestId = "124"
    private val uploadRequestId = "122"
    private val uploadResponseId = "125"
    private val readResponseId = "126"
    private val searchResponseId = "127"
    private val snapshotId = "3"
    private val documentDate = "2023.09.16"
    private val documentName = "documentName"
    private val documentType = "blood general"

    private val jsonKey = "some json key"
    private val jsonValue = "some json value"
    private val snapshotJson = "{\"$jsonKey\":\"$jsonValue\"}"

    private val snapshotMetaDataObjectId = "33"
    private val snapshotMetaDateObject: SnapshotMetaDataObject = SnapshotMetaDataObject(snapshotMetaDataObjectId, "7")

    private val snapshotListObjectId = "56"
    private val userId = "100"
    private val date = "20.03.1999"
    private val nameSnapshotListObject = "snapshot name"

    private val snapshotListObject: SnapshotListObject = SnapshotListObject(
        id = snapshotListObjectId,
        userId = userId,
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
         snapshot = SnapshotSearchFilter("%sasda%")
     )

    private val uploadResponse: DocumentUploadResponse = DocumentUploadResponse(
        requestId = uploadResponseId,
        documentDate = documentDate,
        documentName = documentName,
        result = ResponseResult.SUCCESS,
        documentType = documentType
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
        result = ResponseResult.ERROR,
        list = listOf(snapshotListObject)
    )

    private val documentUploadRequest: DocumentUploadRequest = DocumentUploadRequest(
        requestId = uploadRequestId,
        debug = SnapshotDebug(SnapshotRequestDebugMode.STUB,
            SnapshotRequestDebugStubs.SUCCESS),
        userId = userId,
        file = "some file in base64"
    )

    @Test
    fun `should serialize readRequest` () {
        val json = apiV1Mapper.writeValueAsString(snapshotReadRequest)

        assertContains(json, Regex("stub\":\"success"))
        assertContains(json, Regex("requestType\":\"read"))
        assertContains(json, Regex("mode\":\"${SnapshotRequestDebugMode.TEST.value}"))
        assertContains(json, Regex("requestId\":\"$readRequestId"))
        assertContains(json, Regex("snapshot\":\\{\"id\":\"$snapshotId"))
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

        assertContains(json, Regex("requestType\":\"search"))
        assertContains(json, Regex("stub\":\"badTitle"))
        assertContains(json, Regex("mode\":\"${SnapshotRequestDebugMode.STUB.value}"))
        assertContains(json, Regex("requestId\":\"$searchRequestId"))
        assertContains(json, Regex("snapshot\":\\{\"searchString\":\"$searchString\"}"))
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

        assertContains(json, Regex("mode\":\"${SnapshotRequestDebugMode.STUB.value}"))
        assertContains(json, Regex("stub\":\"${SnapshotRequestDebugStubs.SUCCESS.value}"))
        assertContains(json, Regex("requestId\":\"$uploadRequestId"))
        assertContains(json, Regex("userId\":\"$userId"))
        assertContains(json, Regex("file\":\"some file in base64"))
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

        assertContains(json, Regex("responseType\":\"upload"))
        assertContains(json, Regex("errors\":null"))
        assertContains(json, Regex("documentName\":\"$documentName"))
        assertContains(json, Regex("documentDate\":\"$documentDate"))
        assertContains(json, Regex("documentType\":\"$documentType"))
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

        assertContains(json, Regex("responseType\":\"read"))
        assertContains(json, Regex("requestId\":\"$readResponseId"))
        assertContains(json, Regex("result\":\"${ResponseResult.SUCCESS.value}"))
        assertContains(json, Regex("errors\":null"))
        assertContains(json, Regex("id\":\"$snapshotMetaDataObjectId"))
        assertContains(json, Regex("some json key"))
        assertContains(json, Regex("some json value"))
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

        assertContains(json, Regex("responseType\":\"search"))
        assertContains(json, Regex("requestId\":\"$searchResponseId"))
        assertContains(json, Regex("result\":\"${ResponseResult.ERROR.value}"))
        assertContains(json, Regex("errors\":null"))
        assertContains(json, Regex("id\":\"$snapshotListObjectId"))
        assertContains(json, Regex("userId\":\"$userId"))
        assertContains(json, Regex("date\":\"$date"))
        assertContains(json, Regex("name\":\"$nameSnapshotListObject"))
    }
}