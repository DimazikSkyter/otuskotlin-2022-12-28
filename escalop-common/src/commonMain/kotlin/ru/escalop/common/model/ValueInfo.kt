package ru.escalop.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueInfo(
    val value: Double,
    @SerialName("referent_value") val referentValue: String
)