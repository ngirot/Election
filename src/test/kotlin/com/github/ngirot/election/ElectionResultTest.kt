package com.github.ngirot.election

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class ElectionResultTest {

    @Test
    fun winner_should_return_the_only_one_item_with_a_ranking_of_one() {
        val result = ElectionResult(mapOf("A" to 1, "B" to 2, "C" to 3))
        asserter.assertEquals("Winner is A", "A", result.winner())
    }

    @Test
    fun winner_should_return_null_if_there_is_no_ranking() {
        val result = ElectionResult<String>(emptyMap())
        asserter.assertNull("No winner", result.winner())
    }

    @Test
    fun winner_should_return_null_if_there_is_multiple_item_with_a_ranking_of_one() {
        val result = ElectionResult(mapOf("A" to 1, "B" to 1, "C" to 3))
        asserter.assertNull("No winner", result.winner())
    }
}