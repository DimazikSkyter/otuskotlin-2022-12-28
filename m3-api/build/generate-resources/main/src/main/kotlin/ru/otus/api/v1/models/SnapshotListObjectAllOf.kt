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


import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Список файлов наименования, дата, тип и id
 *
 * @param id Идентификатор снапшота
 * @param date Дата снапшота
 * @param name Имя файла из которого загрузился снапшот
 */


data class SnapshotListObjectAllOf (

    /* Идентификатор снапшота */
    @field:JsonProperty("id")
    val id: java.math.BigDecimal? = null,

    /* Дата снапшота */
    @field:JsonProperty("date")
    val date: kotlin.String? = null,

    /* Имя файла из которого загрузился снапшот */
    @field:JsonProperty("name")
    val name: kotlin.String? = null

)

