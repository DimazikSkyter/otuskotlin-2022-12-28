package ru.escalop.common.model

import kotlinx.datetime.LocalDate

data class Snapshot(val id: Long,
                    val type: DocumentType,
                    val sourceDocumentName: String,
                    val date: LocalDate,
                    val map: List<String>,
                    val name: String)