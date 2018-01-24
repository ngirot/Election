package com.github.ngirot.election.graph

import org.junit.jupiter.api.Test
import kotlin.test.asserter

internal class GraphTest {

    @Test
    fun add_should_set_weight_to_one_from_a_to_b() {
        val graph = Graph<String>()

        graph.add("A", "B")

        val nodes = graph.linksFor("A")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", 1L, nodes.first().weight)
    }

    @Test
    fun multiple_add_should_set_weight_from_a_to_b() {
        val graph = Graph<String>()

        for (i in 1..3) {
            graph.add("A", "B")
        }

        val nodes = graph.linksFor("A")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", 3L, nodes.first().weight)
    }

    @Test
    fun add_from_one_to_b_should_be_cancelled_by_an_add_from_b_to_a() {
        val graph = Graph<String>()

        graph.add("A", "B")
        graph.add("B", "A")

        val nodes = graph.linksFor("A")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", 0L, nodes.first().weight)
    }

    @Test
    fun multiple_add_from_a_to_b_should_decrease_weight_from_b_to_a() {
        val graph = Graph<String>()
        graph.add("A", "B")
        graph.add("A", "B")
        graph.add("A", "B")

        val nodes = graph.linksFor("B")

        asserter.assertEquals("", 1, nodes.size)
        asserter.assertEquals("", -3L, nodes.first().weight)
    }

    @Test
    fun nodeNames_should_return_an_empty_list_when_there_is_no_nodes() {
        asserter.assertTrue("No names", Graph<String>().nodeNames().isEmpty())
    }

    @Test
    fun nodeNames_should_return_all_nodes_values() {
        val graph = Graph<String>()
        graph.add("A", "B")
        graph.add("B", "C")

        asserter.assertTrue("Contains A", graph.nodeNames().contains("A"))
        asserter.assertTrue("Contains B", graph.nodeNames().contains("B"))
        asserter.assertTrue("Contains C", graph.nodeNames().contains("C"))
        asserter.assertEquals("3 values (A, B and C)", 3, graph.nodeNames().size)
    }
}