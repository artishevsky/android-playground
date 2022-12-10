package com.artishevsky.android.playground.feature.retrying

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class RetryingTests {

    @Test
    fun `returns value if no exception`() {
        val wrapped: (String) -> String = { it }
        val retrying: (String) -> String = retry(retries = 1, function = wrapped)
        assertEquals("banana", retrying("banana"))
    }

    @Test
    fun `retries if exception`() {
        var countdown = 1
        val wrapped: (String) -> String = { if (countdown-- == 0) it else error("deliberate") }
        val retrying: (String) -> String = retry(retries = 1, function = wrapped)
        assertEquals("banana", retrying("banana"))
    }

    @Test
    fun `retries if more than one exception`() {
        var countdown = 2
        val wrapped: (String) -> String = { if (countdown-- == 0) it else error("deliberate") }
        val retrying: (String) -> String = retry(retries = 2, function = wrapped)
        assertEquals("banana", retrying("banana"))
    }

    @Test
    fun `doesn't retry if retries is 0`() {
        val wrapped: (String) -> String = { error("deliberate") }
        val retrying: (String) -> String = retry(retries = 0, function = wrapped)
        assertThrows<IllegalStateException> { retrying("banana") }
    }

    @Test
    fun `leaks exception if more errors than retries`() {
        var countdown = 2
        val wrapped: (String) -> String = { if (countdown-- == 0) it else error("deliberate") }
        val retrying: (String) -> String = retry(retries = 1, function = wrapped)
        assertThrows<IllegalStateException> { retrying("banana") }
    }

    @Test
    fun `reports exceptions`() {
        val reported = mutableListOf<Exception>()
        var countdown = 2
        val wrapped: (String) -> String = { if (countdown-- == 0) it else error("deliberate") }
        val retrying: (String) -> String = retry(retries = 2, reported::add, function = wrapped)
        assertEquals("banana", retrying("banana"))
        assertEquals(listOf("deliberate", "deliberate"), reported.map(Exception::message))
    }

}

fun <T, R> retry(
    retries: Int = 1,
    reporter: (Exception) -> Unit = {},
    function: (T) -> R
): (T) -> R {
    return fun(it: T): R {
        var countdown = retries
        while (true) {
            try {
                return function(it)
            } catch (x: Exception) {
                if (countdown-- == 0) throw x
                else reporter(x)
            }
        }
    }
}
