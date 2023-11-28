package ru.otus.common.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Metric(
    val name: String,
    @SerialName("value_info") val valueInfo: ValueInfo
)

