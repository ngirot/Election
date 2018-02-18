package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class RandomBallotTest {

    private val loops = 10000

    @Test
    fun randomBallot_should_return_empty_scores_when_there_is_no_ballot() {
        val scores = RandomBallot.scores(emptySequence<Ballot<String>>())
        asserter.assertTrue("No score", scores.isEmpty())
    }

    @Test
    fun randomBallot_should_return_a_score_first_the_first_element_when_there_is_only_one_ballot() {
        val scores = RandomBallot.scores(sequenceOf(Ballot(listOf("A", "B", "C"))))
        asserter.assertEquals("There is only one score", 1, scores.size)
        asserter.assertNotNull("Score for A", scores["A"])
    }

    @Test
    fun scores_should_be_equivalents_when_ran_multiple_times_when_there_is_the_same_number_of_ballots_for_each_candidates() {
        val ballotA = Ballot(listOf("A"))
        val ballotB = Ballot(listOf("B"))

        var firstIsA = 0
        var firstIsB = 0

        for (i in 1..loops) {
            val scores = RandomBallot.scores(sequenceOf(ballotA, ballotB))
            val scoreA = scores["A"]
            val scoreB = scores["B"]
            if (scoreA != null && scoreB != null) {
                if (scoreA > scoreB) {
                    firstIsA++
                } else {
                    firstIsB++
                }
            }
        }

        val max = loops * 0.75
        asserter.assertTrue("A should not win more than 75% of the time", max > firstIsA)
        asserter.assertTrue("B should not win more than 75% of the time", max > firstIsB)
    }

    @Test
    fun scores_should_not_be_equivalents_when_ran_multiple_times_when_there_is_way_more_ballot_for_one_candidate() {
        val ballotA = Ballot(listOf("A"))
        val ballotB = Ballot(listOf("B"))

        val ballots = sequenceOf(
                createBallotList(5, ballotA),
                createBallotList(95, ballotB))
                .flatten()

        var firstIsA = 0
        var firstIsB = 0

        for (i in 1..loops) {
            val scores = RandomBallot.scores(ballots)
            val scoreA = scores["A"]
            val scoreB = scores["B"]
            if (scoreA != null && scoreB != null) {
                if (scoreA > scoreB) {
                    firstIsA++
                } else {
                    firstIsB++
                }
            }
        }

        asserter.assertTrue("A should not win more than 20% of the time", firstIsA < loops * 0.2)
        asserter.assertTrue("B should not win less than 80% of the time", firstIsB > loops * 0.8)
    }

    private fun <T> createBallotList(number: Int, ballot: Ballot<T>): Sequence<Ballot<T>> {
        val a = mutableListOf<Ballot<T>>()
        for (i in 1..number) {
            a.add(ballot)
        }

        return a.asSequence()
    }
}