package com.github.ngirot.election.method

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class SortitionTest {

    private val loops = 10000

    @Test
    fun scores_should_map_all_candidates() {
        for (i in 1..loops) {
            val scores = Sortition.scores(listOf("A", "B", "C", "D"))

            asserter.assertNotNull("A should be scored", scores["A"])
            asserter.assertNotNull("A should be scored", scores["B"])
            asserter.assertNotNull("A should be scored", scores["C"])
            asserter.assertNotNull("A should be scored", scores["D"])
        }
    }

    @Test
    fun scores_should_assign_a_different_score_to_everyone() {
        val candidates = listOf("A", "B", "C", "D")

        for (i in 1..loops) {
            val scores = Sortition.scores(candidates)

            val differentScores = scores.entries.map { it.value }.distinct()

            asserter.assertEquals("All score are different", candidates.size, differentScores.size)
        }
    }

    @Test
    fun scores_should_be_different_when_ran_multiple_times_with_same_ballots() {
        val candidates = listOf("A", "B")

        var firstIsA = 0
        var firstIsB = 0

        for (i in 1..loops) {
            val scores = Sortition.scores(candidates)
            val scoreA = scores["A"]
            val scoreB = scores["B"]
            if(scoreA!=null && scoreB!=null) {
                if (scoreA > scoreB) {
                    firstIsA++
                } else {
                    firstIsB++
                }
            }
        }

        val max = loops*0.75
        asserter.assertTrue("A should not win more than 75% of the time", max > firstIsA)
        asserter.assertTrue("B should not win more than 75% of the time", max > firstIsB)
    }
}