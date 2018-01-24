package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.graph.Graph

object Condorcet {

    fun <T: Any> countLoss(ballots: Sequence<Ballot<T>>, validator: (Ballot<T>) -> Unit): Map<T, Int> {
        val graph = Graph<T>()

        ballots.forEach { b ->
            validator(b)

            b.extractLinks().forEach { pair ->
                graph.add(pair.first, pair.second)
            }
        }

        return graph.nodeNames()
                .associate{ it to loss(graph, it).count() }
    }

    private fun <T : Any> loss(graph: Graph<T>, nodeName: T) =
            graph.linksFor(nodeName).filter { it.weight < 0 }
}