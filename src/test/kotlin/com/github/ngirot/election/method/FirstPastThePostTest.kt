package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.asserter

internal class FirstPastThePostTest {
    @Test
    fun no_ballot_should_return_empty_count() {
        val scores = FirstPastThePost.score(emptySequence(), this::alwaysPass)
        asserter.assertTrue("No score", scores.isEmpty())
    }

    @Test
    fun should_sum_points_from_one_ballot() {
        val ballots = sequenceOf(Ballot(listOf("A")))
        val scores = FirstPastThePost.score(ballots, this::alwaysPass)

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
        val scores = FirstPastThePost.score(ballots, this::alwaysPass)

        asserter.assertEquals("A has two point", 2, scores["A"])
        asserter.assertEquals("A has three point", 3, scores["B"])
    }

    @Test
    fun should_not_fail_with_no_ballot() {
        FirstPastThePost.score(emptySequence(), this::alwaysFail)
    }

    @Test
    fun should_fail_with_an_invalid_ballot() {
        val ballots = sequenceOf(Ballot(listOf("A", "B", "C")))

        assertFailsWith(Exception::class) {
            FirstPastThePost.score(ballots, this::alwaysFail)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun <T> alwaysPass(b: Ballot<T>) {
    }

    @Suppress("UNUSED_PARAMETER")
    private fun <T> alwaysFail(b: Ballot<T>) {
        throw Exception()
    }
}