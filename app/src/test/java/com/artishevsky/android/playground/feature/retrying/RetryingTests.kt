package com.artishevsky.android.playground.feature.retrying

import org.junit.Assert.assertEquals
import org.junit.Test

class RetryingTests {

    @Test
    fun `returns value if no exception`() {
        val wrapped: (String) -> String = { it }
        val retrying: (String) -> String = retry(wrapped)
        assertEquals("banana", retrying("banana"))
    }

    @Test
    fun `retries if exception`() {
        var countdown = 1
        val wrapped: (String) -> String = { if (countdown-- == 0) it else error("deliberate") }
        val retrying: (String) -> String = retry(wrapped)
        assertEquals("banana", retrying("banana"))
    }

}

fun <T, R> retry(function: (T) -> R): (T) -> R {
    return { it ->
        try {
            function(it)
        } catch (x: Exception) {
            function(it)
        }

    }
}
