package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class BordaTest {

    @Test
    fun there_is_no_score_when_there_is_no_ballots() {
        val scores = Borda.scores<String>(emptySequence(), 0)
        asserter.assertTrue("No score", scores.isEmpty())
    }

    @Test
    fun score_for_a_ballot_is_decreasing_for_each_candidate() {
        val ballot1 = Ballot(listOf("A", "B", "C"))
        val scores = Borda.scores(sequenceOf(ballot1), 3)

        asserter.assertEquals("A have 3 points", 3, scores["A"])
        asserter.assertEquals("B have 2 points", 2, scores["B"])
        asserter.assertEquals("C have 1 points", 1, scores["C"])
    }

    @Test
    fun sum_scores_off_all_ballots() {
        val ballot1 = Ballot(listOf("A", "B", "C"))
        val ballot2 = Ballot(listOf("A", "B", "C"))
        val ballot3 = Ballot(listOf("C", "A", "B"))
        val scores = Borda.scores(sequenceOf(ballot1, ballot2, ballot3), 3)

        asserter.assertEquals("A have 3 points", 8, scores["A"])
        asserter.assertEquals("B have 2 points", 5, scores["B"])
        asserter.assertEquals("C have 1 points", 5, scores["C"])
    }
}