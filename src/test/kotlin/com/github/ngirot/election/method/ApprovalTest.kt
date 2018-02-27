package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class ApprovalTest {

    @Test
    fun no_ballots_should_return_empty_map() {
        val scores = Approval.scores(emptySequence<Ballot<String>>())

        asserter.assertTrue("No scores without ballot", scores.isEmpty())
    }

    @Test
    fun should_count_all_vote_from_any_ballots() {
        val ballot1 = Ballot(listOf("A"))
        val ballot2 = Ballot(listOf("A", "B"))
        val ballot3 = Ballot(listOf("B", "C"))

        val scores = Approval.scores(sequenceOf(ballot1, ballot2, ballot3))

        asserter.assertEquals("A has two vote", scores["A"], 2)
        asserter.assertEquals("B has two vote", scores["B"], 2)
        asserter.assertEquals("C has one vote", scores["C"], 1)
    }
}