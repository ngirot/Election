package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class ElectionIT {

    @Test
    fun test_condorcet_with_a_winner_IT() {
        val elections = Election(listOf("A", "B", "C"))

        val ballot1 = Ballot(listOf("A", "C", "B"))
        val ballot2 = Ballot(listOf("B", "C", "A"))
        val ballot3 = Ballot(listOf("C", "B", "A"))
        val ballot4 = Ballot(listOf("C", "A", "B"))

        val ballots = sequenceOf(createBallotList(23, ballot1),
                createBallotList(19, ballot2),
                createBallotList(16, ballot3),
                createBallotList(2, ballot4)).flatten()

        val result = elections.condorcet(ballots)

        asserter.assertEquals("The winner is C", "C", result)
    }

    @Test
    fun test_condorcet_paradox_IT() {
        val elections = Election(listOf("X", "Y", "Z"))

        val ballot1 = Ballot(listOf("X", "Y", "Z"))
        val ballot2 = Ballot(listOf("Y", "Z", "X"))
        val ballot3 = Ballot(listOf("Z", "X", "Y"))

        val ballots = sequenceOf(createBallotList(41, ballot1),
                createBallotList(33, ballot2),
                createBallotList(22, ballot3)).flatten()

        val result = elections.condorcet(ballots)

        asserter.assertNull("There is a paradox", result)
    }

    @Test
    fun test_firstPastThePost_with_a_winner_IT() {
        val election = Election(listOf("A", "B", "C"))

        val ballot1 = Ballot(listOf("A"))
        val ballot2 = Ballot(listOf("B"))
        val ballot3 = Ballot(listOf("C"))

        val ballotSequence = sequenceOf(
                createBallotList(5, ballot1),
                createBallotList(17, ballot2),
                createBallotList(8, ballot3))
                .flatten()

        val elected = election.firstPastThePost(ballotSequence)

        asserter.assertEquals("The winner is B", "B", elected)
    }

    @Test
    fun test_firstPastThePost_with_equality_IT() {
        val election = Election(listOf("A", "B", "C"))

        val ballot1 = Ballot(listOf("A"))
        val ballot2 = Ballot(listOf("B"))
        val ballot3 = Ballot(listOf("C"))

        val ballotSequence = sequenceOf(
                createBallotList(5, ballot1),
                createBallotList(10, ballot2),
                createBallotList(10, ballot3))
                .flatten()

        val elected = election.firstPastThePost(ballotSequence)

        asserter.assertNull("There is no winner", elected)
    }

    private fun <T> createBallotList(number: Int, ballot: Ballot<T>): Sequence<Ballot<T>> {
        val a = mutableListOf<Ballot<T>>()
        for (i in 1..number) {
            a.add(ballot)
        }

        return a.asSequence()
    }
}