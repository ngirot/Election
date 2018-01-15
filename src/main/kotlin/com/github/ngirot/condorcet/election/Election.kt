package com.github.ngirot.condorcet.election

import com.github.ngirot.condorcet.graph.Graph

class Election<T>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): T? {
        val graph = Graph<T>()

        votes.forEach { b ->
            if (!candidates.containsAll(b.orderOfPreference)) {
                throw InvalidBallotException()
            }

            b.extractLinks().forEach { pair ->
                graph.add(pair.first, pair.second)
            }
        }

        val winners = candidates.filter { graph.nodesFor(it).filter { it.weight < 0 }.count() == 0 }

        return if (winners.size != 1) {
            null
        } else {
            winners.first()
        }
    }
}