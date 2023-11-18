package ru.otus.entrails

import org.junit.jupiter.api.Assertions
import ru.otus.common.model.DocumentType
import kotlin.test.Test


class PdfTextExtractorStubTest {

    @Test
    fun extractGeneralBlood() {
        val extractText = PdfTextExtractorStub.extract(DocumentType.BLOOD_GENERAL, "asd".toByteArray())
        println("General blood json = \n$extractText")
        Assertions.assertAll(
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"СОЭ\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Лейкоциты\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Эритроциты\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Гематокрит\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Тромбоциты\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Нейтрофилы\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Лимфоциты\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Эозинофилы\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Базинофилы\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Моноциты\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
        )
    }

    @Test
    fun extractChemistryBlood() {
        val extractText = PdfTextExtractorStub.extract(DocumentType.BLOOD_CHEMISTRY, "asd".toByteArray())
        println("Chemistry blood json = \n$extractText")
        Assertions.assertAll(
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"АлАТ\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"АсАТ\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"ГГТ\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Креатинин\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Мочевина\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Билирубин\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Глюкоза\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
            { Assertions.assertTrue { extractText.contains(Regex("\\{\"name\":\"Холестерин\",\"value_info\":\\{\"value\":[0-9]+.[0-9]+,\"referent_value\":\"\\d+-\\d+\"}}")) } },
        )
    }
}