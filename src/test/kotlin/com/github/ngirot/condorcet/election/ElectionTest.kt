package com.github.ngirot.condorcet.election

import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.asserter

internal class ElectionTest {

    @Test
    fun condorcet_vote_with_no_ballot_should_elect_no_one() {
        val election = Election<String>(emptyList())

        val elected = election.condorcet(emptySequence())

        asserter.assertNull("No one should win", elected)
    }

    @Test
    fun condorcet_vote_with_a_ballot_should_elect_the_first_of_the_sequence() {
        val election = Election(listOf("first", "second", "third"))
        val ballot = Ballot(listOf("first", "second", "third"))

        val elected = election.condorcet(sequenceOf(ballot))

        asserter.assertEquals("Should be the first element", "first", elected)
    }

    @Test
    fun condorcet_vote_should_fail_if_someone_vote_for_a_not_candidate() {
        val election = Election(listOf("X", "Y"))
        val ballot = Ballot(listOf("X", "Z"))

        assertFailsWith(InvalidBallotException::class) {
            election.condorcet(sequenceOf(ballot))
        }
    }

    @Test
    fun condorcet_vote_with_a_candidate_winning_all_duel_should_be_elected() {
        val election = Election(listOf("a", "b", "c"))

        val ballot1 = Ballot(listOf("a", "c", "b"))
        val ballot2 = Ballot(listOf("b", "c", "a"))
        val ballot3 = Ballot(listOf("c", "b", "a"))
        val ballot4 = Ballot(listOf("c", "a", "b"))

        val ballotSequence = sequenceOf(
                createBallotList(23, ballot1),
                createBallotList(19, ballot2),
                createBallotList(16, ballot3),
                createBallotList(2, ballot4))
                .flatten()
                .asSequence()

        val elected = election.condorcet(ballotSequence)

        asserter.assertEquals("Should be the first element", "c", elected)
    }

    @Test
    fun condorcet_paradoxe_when_no_one_win_all_duels() {
        val election = Election(listOf("A", "B", "C"))

        val ballot1 = Ballot(listOf("A", "B", "C"))
        val ballot2 = Ballot(listOf("B", "C", "A"))
        val ballot3 = Ballot(listOf("B", "A", "C"))
        val ballot4 = Ballot(listOf("C", "A", "B"))
        val ballot5 = Ballot(listOf("C", "B", "A"))

        val ballotSequence = sequenceOf(
                createBallotList(23, ballot1),
                createBallotList(17, ballot2),
                createBallotList(2, ballot3),
                createBallotList(10, ballot4),
                createBallotList(8, ballot5))
                .flatten()
                .asSequence()

        val elected = election.condorcet(ballotSequence)

        asserter.assertNull("There is not Condorcet winner", elected)
    }


    private fun <T> createBallotList(number: Int, ballot: Ballot<T>): Sequence<Ballot<T>> {
        val a = mutableListOf<Ballot<T>>()
        for (i in 1..number) {
            a.add(ballot)
        }

        return a.asSequence()
    }
}