package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
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

        val elected = election.condorcet(ballotSequence)

        asserter.assertNull("There is not Condorcet winner", elected)
    }

    @Test
    fun firstPastThePost_with_no_ballot_should_elect_no_one() {
        val election = Election(listOf("A", "B"))

        var elected = election.firstPastThePost(sequenceOf())

        asserter.assertNull("Should not have a winner", elected)
    }

    @Test
    fun firstPastThePost_vote_with_a_single_ballot_should_elect_the_first_of_the_sequence() {
        val election = Election(listOf("A", "B"))
        val ballot = Ballot(listOf("A"))

        val elected = election.firstPastThePost(sequenceOf(ballot))

        asserter.assertEquals("Should be the single ballot name", "A", elected)
    }

    @Test
    fun firstPastThePost_vote_should_fail_if_someone_vote_for_a_not_candidate() {
        val election = Election(listOf("X", "Y"))
        val ballot = Ballot(listOf("Z"))

        assertFailsWith(InvalidBallotException::class) {
            election.firstPastThePost(sequenceOf(ballot))
        }
    }

    @Test
    fun firstPastThePost_vote_should_elected_the_candidate_with_the_most_ballot() {
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

        asserter.assertEquals("Should be B", "B", elected)
    }

    @Test
    fun firstPastThePost_vote_should_elected_no_one_if_two_candidate_haev_the_same_amount_of_ballot() {
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

        asserter.assertNull("Should not have a winner", elected)
    }


    private fun <T> createBallotList(number: Int, ballot: Ballot<T>): Sequence<Ballot<T>> {
        val a = mutableListOf<Ballot<T>>()
        for (i in 1..number) {
            a.add(ballot)
        }

        return a.asSequence()
    }
}