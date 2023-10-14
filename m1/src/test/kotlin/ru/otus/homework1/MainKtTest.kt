package ru.otus.homework1

import java.lang.Integer.sum
import kotlin.test.Test
import kotlin.test.assertEquals


internal class MainKtTest {

    @Test
    fun shouldSumReturn10From5And5() {
        assertEquals(/* expected = */ 10, /* actual = */ sum(5,5))

    }
}