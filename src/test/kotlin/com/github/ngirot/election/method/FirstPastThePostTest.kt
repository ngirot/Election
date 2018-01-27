package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.asserter

internal class FirstPastThePostTest {
    @Test
    fun no_ballot_should_return_empty_count() {
        val scores = FirstPastThePost.score(emptySequence())
        asserter.assertTrue("No score", scores.isEmpty())
    }

    @Test
    fun should_sum_points_from_one_ballot() {
        val ballots = sequenceOf(Ballot(listOf("A")))
        val scores = FirstPastThePost.score(ballots)

        asserter.assertEquals("A has one point", 1, scores["A"])
    }

    @Test
    fun should_sum_points_from_multiple_ballot() {
        val ballot1 = Ballot(listOf("B"))
        val ballot2 = Ballot(listOf("A"))
        val ballot3 = Ballot(listOf("B"))
        val ballot4 = Ballot(listOf("B"))
        val ballot5 = Ballot(listOf("A"))


        val ballots = sequenceOf(ballot1, ballot2, ballot3, ballot4, ballot5)
        val scores = FirstPastThePost.score(ballots)

        asserter.assertEquals("A has two point", 2, scores["A"])
        asserter.assertEquals("A has three point", 3, scores["B"])
    }
}