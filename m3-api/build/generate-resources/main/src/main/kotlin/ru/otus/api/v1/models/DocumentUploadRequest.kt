/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package ru.otus.api.v1.models

import ru.otus.api.v1.models.DocumentUploadObject
import ru.otus.api.v1.models.IRequest
import ru.otus.api.v1.models.SnapshotDebug

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 *
 * @param userId Идентификатор пользователя
 * @param requestType Поле-дескриминатор для вычисления типа запроса
 * @param requestId Идентификатор запроса для отладки и идемпотентности
 * @param debug 
 * @param uploadMetaData 
 */


data class DocumentUploadRequest (

    /* Идентификатор пользователя */
    @field:JsonProperty("userId")
    override val userId: kotlin.String? = null,

    /* Поле-дескриминатор для вычисления типа запроса */
    @field:JsonProperty("requestType")
    override val requestType: kotlin.String? = null,

    /* Идентификатор запроса для отладки и идемпотентности */
    @field:JsonProperty("requestId")
    override val requestId: kotlin.String? = null,

    @field:JsonProperty("debug")
    val debug: SnapshotDebug? = null,

    @field:JsonProperty("uploadMetaData")
    val uploadMetaData: DocumentUploadObject? = null

) : IRequest
