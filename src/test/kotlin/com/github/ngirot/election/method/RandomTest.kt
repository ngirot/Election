package com.github.ngirot.election.method

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class RandomTest {

    @Test
    fun scores_should_map_all_candidates() {
        for (i in 1..1000) {
            val scores = Random.scores(listOf("A", "B", "C", "D"))

            asserter.assertNotNull("A should be scored", scores["A"])
            asserter.assertNotNull("A should be scored", scores["B"])
            asserter.assertNotNull("A should be scored", scores["C"])
            asserter.assertNotNull("A should be scored", scores["D"])
        }
    }

    @Test
    fun scores_should_assigne_a_different_score_to_everyone() {
        val candidates = listOf("A", "B", "C", "D")

        for (i in 1..1000) {
            val scores = Random.scores(candidates)

            val differentScores = scores.entries.map { it.value }.distinct()

            asserter.assertEquals("All score are different", candidates.size, differentScores.size)
        }
    }
}