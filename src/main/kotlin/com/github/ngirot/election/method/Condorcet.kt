package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.graph.Graph

object Condorcet {

    fun <T> countLoss(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        val graph = Graph<T>()

        ballots.flatMap { it.extractDuelsWon().asSequence() }
                .forEach { pair -> graph.add(pair.first, pair.second) }

        return graph.nodeNames()
                .associate { it to lossCounter(graph, it) }
    }

    private fun <T> lossCounter(graph: Graph<T>, nodeName: T) =
            graph.linksFor(nodeName)
                    .filter { it.weight < 0 }
                    .count()
}