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

import ru.otus.api.v1.models.Error
import ru.otus.api.v1.models.ResponseResult

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Базовый интерфейс для всех ответов
 *
 * @param userId Идентификатор пользователя
 * @param responseType Поле-дескриминатор для вычисления типа запроса
 * @param requestId Идентификатор запроса для отладки
 * @param result 
 * @param errors 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "responseType", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = SnapshotReadResponse::class, name = "read"),
    JsonSubTypes.Type(value = SnapshotSearchResponse::class, name = "search"),
    JsonSubTypes.Type(value = DocumentUploadResponse::class, name = "upload")
)

interface IResponse {

    /* Идентификатор пользователя */
    @get:JsonProperty("userId")
    val userId: kotlin.String?
    /* Поле-дескриминатор для вычисления типа запроса */
    @get:JsonProperty("responseType")
    val responseType: kotlin.String?
    /* Идентификатор запроса для отладки */
    @get:JsonProperty("requestId")
    val requestId: kotlin.String?
    @get:JsonProperty("result")
    val result: ResponseResult?
    @get:JsonProperty("errors")
    val errors: kotlin.collections.List<Error>?
}

