package com.github.ngirot.election.ballot

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class BallotTest {

    @Test
    fun ballot_should_contains_links_for_all_items_in_order() {
        val ballot = Ballot(listOf("a", "b", "c"))

        val links = ballot.extractLinks()

        asserter.assertTrue("a -> b", links.contains(Pair("a", "b")))
        asserter.assertTrue("a -> c", links.contains(Pair("a", "c")))
        asserter.assertTrue("b -> c", links.contains(Pair("b", "c")))
    }

    @Test
    fun ballot_should_not_contains_links_in_wrong_order() {
        val ballot = Ballot(listOf("a", "b", "c"))

        val links = ballot.extractLinks()

        asserter.assertTrue("c -> a", !links.contains(Pair("c", "a")))
        asserter.assertTrue("c -> b", !links.contains(Pair("c", "b")))
        asserter.assertTrue("b -> a", !links.contains(Pair("b", "a")))
    }
}