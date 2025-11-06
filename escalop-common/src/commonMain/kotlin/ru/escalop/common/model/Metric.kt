package ru.escalop.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metric(
    val name: String,
    @SerialName("value_info") val valueInfo: ValueInfo
)

