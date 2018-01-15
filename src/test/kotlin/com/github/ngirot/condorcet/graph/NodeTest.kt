package com.github.ngirot.condorcet.graph

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class NodeTest {

    @Test
    fun increase_should_increment_the_weight() {
        val node = Node(null, null, 1)
        node.increase()
        asserter.assertEquals("Weight", 2L, node.weight)
    }

    @Test
    fun decrease_should_decrement_the_weight() {
        val node = Node(null, null, 1)
        node.decrease()
        asserter.assertEquals("Weight", 0L, node.weight)
    }
}