package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class InstantRunoffTest {

    @Test
    fun should_have_no_score_when_there_is_no_ballot() {
        val scores = InstantRunoff.scores(emptySequence<Ballot<String>>())
        asserter.assertTrue("No score", scores.isEmpty())
    }

    @Test
    fun item_should_be_scored_in_order_with_a_single_ballot() {
        val scores = InstantRunoff.scores(sequenceOf(Ballot(listOf("A", "B", "C"))))

        asserter.assertTrue("A should have a better score than B", scores.getValue("A") > scores.getValue("B"))
        asserter.assertTrue("A should have a better score than C", scores.getValue("A") > scores.getValue("C"))
        asserter.assertTrue("B should have a better score than C", scores.getValue("B") > scores.getValue("C"))
    }

    @Disabled
    @Test
    fun item_with_absolute_majority_should_have_a_better_score_than_others() {
        val ballot1 = Ballot(listOf("A", "B", "C"))
        val ballot2 = Ballot(listOf("B", "A", "C"))

        val scores = InstantRunoff.scores(sequenceOf(ballot1, ballot2, ballot2))

        asserter.assertTrue("B should have a better score than A", scores.getValue("B") > scores.getValue("A"))
        asserter.assertTrue("B should have a better score than C", scores.getValue("B") > scores.getValue("C"))
        asserter.assertTrue("A should have a better score than C", scores.getValue("A") > scores.getValue("C"))
    }
}