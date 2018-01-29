package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class CondorcetTest {

    @Test
    fun no_ballot_should_return_empty_count() {
        val scores = Condorcet.countLoss<String>(emptySequence())
        asserter.assertTrue("No score", scores.isEmpty())
    }

    @Test
    fun should_count_loss_from_one_ballot() {
        val ballots = sequenceOf(Ballot(listOf("A", "B", "C")))
        val scores = Condorcet.countLoss(ballots)

        asserter.assertEquals("A never lose", 0, scores["A"])
        asserter.assertEquals("B lose against A", 1, scores["B"])
        asserter.assertEquals("C lose against B and C", 2, scores["C"])
    }


    @Test
    fun should_count_loss_from_multiple_ballot_as_a_tree() {
        val ballot1 = Ballot(listOf("A", "B", "C"))
        val ballot2 = Ballot(listOf("A", "C", "B"))
        val ballot3 = Ballot(listOf("B", "A", "C"))

        val ballots = sequenceOf(ballot1, ballot2, ballot3)
        val scores = Condorcet.countLoss(ballots)

        asserter.assertEquals("A never lose", 0, scores["A"])
        asserter.assertEquals("B lose against A", 1, scores["B"])
        asserter.assertEquals("C lose against B and C", 2, scores["C"])
    }

    @Test
    fun should_count_loss_from_multiple_ballot_as_a_cyclic_graph() {
        val ballot1 = Ballot(listOf("A", "B", "C"))
        val ballot2 = Ballot(listOf("C", "A", "B"))
        val ballot3 = Ballot(listOf("B", "C", "A"))

        val ballots = sequenceOf(ballot1, ballot2, ballot3)
        val scores = Condorcet.countLoss(ballots)

        asserter.assertEquals("A lose against C", 1, scores["A"])
        asserter.assertEquals("B lose against A", 1, scores["B"])
        asserter.assertEquals("C lose against B", 1, scores["C"])
    }
}