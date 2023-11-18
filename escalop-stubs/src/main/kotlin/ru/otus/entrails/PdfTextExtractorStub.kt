package ru.otus.entrails

import kotlinx.datetime.LocalDate
import ru.otus.common.model.DocumentType
import ru.otus.exceptions.UndefinedDocumentTypeException
import kotlin.random.Random


object PdfTextExtractorStub {

    private val examples: Map<DocumentType, () -> String> = mapOf(
        Pair(DocumentType.UNDEFINED) { throw UndefinedDocumentTypeException("Failed to parse document with undefined type") },
        Pair(DocumentType.BLOOD_GENERAL) { bloodExample() },
        Pair(DocumentType.BLOOD_CHEMISTRY) { bloodChemistry() }
    )

    private val random: Random = Random.Default

    fun extract(documentType: DocumentType, pdf: ByteArray): String {
        return examples[documentType]?.invoke()
            ?: throw UndefinedDocumentTypeException("Failed to parse document with undefined type")
    }

    fun extractDate(pdf: ByteArray): LocalDate {
        return LocalDate.parse("2023-09-05")
    }

    private fun bloodExample() = """
        [
             ${fillElement("СОЭ", 0.0, 15.0, "0-15")},
             ${fillElement("Лейкоциты", 0.0, 15.0, "0-15")},
             ${fillElement("Эритроциты", 0.0, 15.0, "0-15")},
             ${fillElement("Гематокрит", 0.0, 15.0, "0-15")},
             ${fillElement("Тромбоциты", 0.0, 15.0, "0-15")},
             ${fillElement("Нейтрофилы", 0.0, 15.0, "0-15")},
             ${fillElement("Лимфоциты", 0.0, 15.0, "0-15")},
             ${fillElement("Эозинофилы", 0.0, 15.0, "0-15")},
             ${fillElement("Базинофилы", 0.0, 15.0, "0-15")},
             ${fillElement("Моноциты", 0.0, 15.0, "0-15")}
        ]
    """.trimIndent()

    private fun bloodChemistry() = """
        [
            ${fillElement("АлАТ", 0.0, 15.0, "0-15")},
            ${fillElement("АсАТ", 0.0, 15.0, "0-15")},
            ${fillElement("ГГТ", 0.0, 16.0, "0-16")},
            ${fillElement("Креатинин", 0.0, 16.0, "0-16")},
            ${fillElement("Мочевина", 0.0, 15.0, "0-15")},
            ${fillElement("Билирубин", 4.0, 15.0, "4-15")},
            ${fillElement("Глюкоза", 4.0, 15.0, "4-15")},
            ${fillElement("Холестерин", 0.0, 15.0, "0-15")}
        ]
    """.trimIndent()

    private fun fillElement(name: String, min: Double, max: Double, normal: String): String {
        return "{\"name\":\"$name\",\"value_info\":{\"value\":${
            Math.round(random.nextDouble(min, max) * 100).toDouble() / 100.0
        },\"referent_value\":\"$normal\"}}"
    }
}