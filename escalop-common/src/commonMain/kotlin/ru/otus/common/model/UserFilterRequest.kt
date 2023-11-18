package ru.otus.common.model

import kotlinx.datetime.Instant

data class UserFilterRequest (
    var namePart: String?,
    var date: Instant?,
    var type: DocumentType?
) {
    constructor(str: String) : this(null, null, null ) {
        str.split("&").map {
            val parts = it.split("=")
            if (parts.size == 2) Pair<String, String>(parts[0], parts[1]) else null
        }.filterNotNull().forEach {
            when (it.first) {
                "namePart" -> {
                    this.namePart = it.second
                }
                "date" -> {
                    this.date = Instant.parse(it.second)
                }
                "type" -> {
                    this.type = DocumentType.valueOf(it.second)
                }
            }
        }
    }
    companion object {
        val NONE = UserFilterRequest(null, null, null)
    }
}
