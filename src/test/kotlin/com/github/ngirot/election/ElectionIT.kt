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

        asserter.assertEquals("The winner is C", "C", result.winner())
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

        asserter.assertNull("There is a paradox", result.winner())
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

        val result = election.firstPastThePost(ballotSequence)

        asserter.assertEquals("The winner is B", "B", result.winner())
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

        val result = election.firstPastThePost(ballotSequence)

        asserter.assertNull("There is no winner", result.winner())
    }

    @Test
    fun test_borda_with_a_winner_IT() {
        val election = Election(listOf("A", "B", "C", "D"))

        val ballot1 = Ballot(listOf("A", "B", "C", "D"))
        val ballot2 = Ballot(listOf("B", "C", "D", "A"))
        val ballot3 = Ballot(listOf("C", "D", "B", "A"))
        val ballot4 = Ballot(listOf("D", "C", "B", "A"))


        val ballotSequence = sequenceOf(
                createBallotList(42, ballot1),
                createBallotList(26, ballot2),
                createBallotList(15, ballot3),
                createBallotList(17, ballot4))
                .flatten()

        val result = election.borda(ballotSequence)

        asserter.assertEquals("The winner is B", "B", result.winner())
    }

    @Test
    fun test_borda_with_equality_IT() {
        val election = Election(listOf("A", "B", "C"))

        val ballot1 = Ballot(listOf("A", "B", "C"))
        val ballot2 = Ballot(listOf("B", "A", "C"))


        val ballotSequence = sequenceOf(
                createBallotList(25, ballot1),
                createBallotList(25, ballot2))
                .flatten()

        val result = election.borda(ballotSequence)

        asserter.assertNull("The is no winner", result.winner())
    }

    @Test
    fun test_sortition_should_elect_something() {
        val election = Election(listOf("A", "B", "C"))

        val elected = election.sortition()

        asserter.assertNotNull("There is always a winner", elected.winner())
    }

    @Test
    fun test_randomBallot_should_elect_something() {
        val election = Election(listOf("A", "B", "C"))

        val elected = election.sortition()

        asserter.assertNotNull("There is always a winner", elected.winner())
    }

    @Test
    fun test_approval_should_elect_something() {
        val election = Election(listOf("A", "B", "C", "D"))

        val ballot1 = Ballot(listOf("A"))
        val ballot2 = Ballot(listOf("B", "D"))
        val ballot3 = Ballot(listOf("A", "B", "C"))
        val ballot4 = Ballot(listOf("B", "C", "D"))


        val ballotSequence = sequenceOf(
                createBallotList(42, ballot1),
                createBallotList(26, ballot2),
                createBallotList(15, ballot3),
                createBallotList(17, ballot4))
                .flatten()

        val result = election.approval(ballotSequence)

        asserter.assertEquals("The winner is B", "B", result.winner())
    }

    private fun <T> createBallotList(number: Int, ballot: Ballot<T>): Sequence<Ballot<T>> {
        val a = mutableListOf<Ballot<T>>()
        for (i in 1..number) {
            a.add(ballot)
        }

        return a.asSequence()
    }
}