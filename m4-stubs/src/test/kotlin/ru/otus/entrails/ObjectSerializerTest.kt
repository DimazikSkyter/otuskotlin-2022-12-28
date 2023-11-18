package ru.otus.entrails

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.otus.common.entity.Metric

class ObjectSerializerTest {

    val generalBloodJson = """[
        {"name":"СОЭ","value_info":{"value":5.63,"referent_value":"0-15"}},
        {"name":"Лейкоциты","value_info":{"value":9.72,"referent_value":"0-15"}},
        {"name":"Эритроциты","value_info":{"value":1.68,"referent_value":"0-15"}},
        {"name":"Гематокрит","value_info":{"value":1.34,"referent_value":"0-15"}},
        {"name":"Тромбоциты","value_info":{"value":4.47,"referent_value":"0-15"}},
        {"name":"Нейтрофилы","value_info":{"value":14.77,"referent_value":"0-15"}},
        {"name":"Лимфоциты","value_info":{"value":4.97,"referent_value":"0-15"}},
        {"name":"Эозинофилы","value_info":{"value":11.47,"referent_value":"0-15"}},
        {"name":"Базинофилы","value_info":{"value":1.69,"referent_value":"0-15"}},
        {"name":"Моноциты","value_info":{"value":5.56,"referent_value":"0-15"}}
    ]""".trimIndent()

    val chemistryBloodJson = """
    [
        {"name":"АлАТ","value_info":{"value":10.23,"referent_value":"0-15"}},
        {"name":"АсАТ","value_info":{"value":3.31,"referent_value":"0-15"}},
        {"name":"ГГТ","value_info":{"value":10.02,"referent_value":"0-16"}},
        {"name":"Креатинин","value_info":{"value":3.3,"referent_value":"0-16"}},
        {"name":"Мочевина","value_info":{"value":5.79,"referent_value":"0-15"}},
        {"name":"Билирубин","value_info":{"value":12.46,"referent_value":"4-15"}},
        {"name":"Глюкоза","value_info":{"value":12.07,"referent_value":"4-15"}},
        {"name":"Холестерин","value_info":{"value":2.68,"referent_value":"0-15"}}
    ]    
    """.trimIndent()


    @Test
    fun convertGeneralBlood() {
        val metricList: List<Metric> = ObjectSerializer.convert(generalBloodJson)

        val listMetricNames = listOf("Лейкоциты", "Эритроциты", "Гематокрит", "Тромбоциты", "Нейтрофилы", "Лимфоциты", "Эозинофилы", "Базинофилы")
        val fullMetricNames = metricList.map { metric: Metric -> metric.name }
        assertEquals(10, metricList.size)
        listMetricNames.forEach {
            name ->
            assertTrue(fullMetricNames.contains(name))
        }
    }

    @Test
    fun convertChemistryBlood() {
        val metricList: List<Metric> = ObjectSerializer.convert(chemistryBloodJson)
        assertEquals(8, metricList.size)
    }
}