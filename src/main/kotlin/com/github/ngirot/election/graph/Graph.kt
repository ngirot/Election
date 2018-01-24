package com.github.ngirot.election.graph

class Graph<T> {

    private val nodes = mutableListOf<Node<T>>()

    fun add(from: T, to: T) {
        val existing = nodes.find { node -> node.from == from && node.to == to}
        val reverse = nodes.find { node -> node.to == from && node.from == to }

        if (existing != null) {
            existing.increase()
        } else {
            nodes.add(Node(from, to, 1))
        }

        if (reverse != null) {
            reverse.decrease()
        } else {
            nodes.add(Node(to, from, -1))
        }
    }

    fun linksFor(nodeName: T): List<Node<T>> {
        return nodes.filter { it.from == nodeName }
    }

    fun nodeNames(): List<T> {
        return nodes.flatMap { listOf(it.from, it.to) }.distinct()
    }

    override fun toString(): String {
        return "Graph(nodes=$nodes)"
    }


}