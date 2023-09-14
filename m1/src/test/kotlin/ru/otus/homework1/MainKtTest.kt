package ru.otus.homework1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainKtTest {

    @Test
    fun shouldSumReturn10From5And5() {
        assertEquals(/* expected = */ 10, /* actual = */ sum(a = 5, b = 5))
    }
}