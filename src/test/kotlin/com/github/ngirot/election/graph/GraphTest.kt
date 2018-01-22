package com.github.ngirot.election.graph

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class GraphTest {

    @Test
    fun add_should_set_weight_to_one_from_a_to_b() {
        val graph = Graph<String>()

        graph.add("A", "B")

        val nodes = graph.nodesFor("A")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", 1L, nodes.first().weight)
    }

    @Test
    fun multiple_add_should_set_weight_from_a_to_b() {
        val graph = Graph<String>()

        for (i in 1..3) {
            graph.add("A", "B")
        }

        val nodes = graph.nodesFor("A")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", 3L, nodes.first().weight)
    }

    @Test
    fun add_from_one_to_b_should_be_cancelled_by_an_add_from_b_to_a() {
        val graph = Graph<String>()

        graph.add("A", "B")
        graph.add("B", "A")

        val nodes = graph.nodesFor("A")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", 0L, nodes.first().weight)
    }

    @Test
    fun multiple_add_from_a_to_b_should_decrease_weight_from_b_to_a() {
        val graph = Graph<String>()
        graph.add("A", "B")
        graph.add("A", "B")
        graph.add("A", "B")

        val nodes = graph.nodesFor("B")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", -3L, nodes.first().weight)
    }
}