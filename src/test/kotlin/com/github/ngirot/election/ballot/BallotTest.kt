package com.github.ngirot.election.ballot

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class BallotTest {

    @Test
    fun ballot_should_contains_links_for_all_items_in_order() {
        val ballot = Ballot(listOf("a", "b", "c"))

        val links = ballot.extractDuelsWon()

        asserter.assertTrue("a -> b", links.contains("a" to "b"))
        asserter.assertTrue("a -> c", links.contains("a" to "c"))
        asserter.assertTrue("b -> c", links.contains("b" to "c"))
    }

    @Test
    fun ballot_should_not_contains_links_in_wrong_order() {
        val ballot = Ballot(listOf("a", "b", "c"))

        val links = ballot.extractDuelsWon()

        asserter.assertTrue("c -> a", !links.contains("c" to "a"))
        asserter.assertTrue("c -> b", !links.contains("c" to "b"))
        asserter.assertTrue("b -> a", !links.contains("b" to "a"))
    }
}