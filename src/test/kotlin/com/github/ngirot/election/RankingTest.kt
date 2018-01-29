package com.github.ngirot.election

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class RankingTest {

    @Test
    fun byHigherScore_should_rank_empty_map() {
        val rank = Ranking.byHigherScore(emptyMap<String, Int>())
        asserter.assertTrue("No rank", rank.isEmpty())
    }

    @Test
    fun byHigherScore_should_rank_a_single_entry_as_first() {
        val rank = Ranking.byHigherScore(mapOf("A" to 150))
        asserter.assertEquals("A is first", 1, rank["A"])
    }

    @Test
    fun byHigherScore_should_rank_multiple_entry_by_score() {
        val rank = Ranking.byHigherScore(mapOf("C" to -12, "A" to 150, "B" to 120))
        asserter.assertEquals("A is first", 1, rank["A"])
        asserter.assertEquals("B is second", 2, rank["B"])
        asserter.assertEquals("C is third", 3, rank["C"])
    }

    @Test
    fun byHigherScore_should_rank_same_score_at_same_rank() {
        val rank = Ranking.byHigherScore(mapOf("A" to 100, "B" to 100))
        asserter.assertEquals("A and B are equals", rank["B"], rank["A"])
    }

    @Test
    fun byLowerScore_should_rank_empty_map() {
        val rank = Ranking.byLowerScore(emptyMap<String, Int>())
        asserter.assertTrue("No rank", rank.isEmpty())
    }

    @Test
    fun byLowerScore_should_rank_a_single_entry_as_first() {
        val rank = Ranking.byLowerScore(mapOf("A" to 150))
        asserter.assertEquals("A is first", 1, rank["A"])
    }

    @Test
    fun byLowerScore_should_rank_multiple_entry_by_score() {
        val rank = Ranking.byLowerScore(mapOf("C" to -12, "A" to 150, "B" to 120))
        asserter.assertEquals("A is first", 3, rank["A"])
        asserter.assertEquals("B is second", 2, rank["B"])
        asserter.assertEquals("C is third", 1, rank["C"])
    }

    @Test
    fun byLowerScore_should_rank_same_score_at_same_rank() {
        val rank = Ranking.byLowerScore(mapOf("A" to 100, "B" to 100))
        asserter.assertEquals("A and B are equals", rank["B"], rank["A"])
    }
}