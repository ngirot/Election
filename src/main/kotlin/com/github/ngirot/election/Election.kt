package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
import com.github.ngirot.election.graph.Graph

class Election<T>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): T? {
        val graph = Graph<T>()

        votes.forEach { b ->
            checkBallotValidity(b)

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

    fun firstPastThePost(votes: Sequence<Ballot<T>>): T? {
        val counts = votes.map { checkBallotValidity(it);it.first() }
                .filter { it != null }
                .groupBy { it }
                .mapValues { it.value.size }

        val max = counts.maxBy { it.value }?.value ?: return null

        val winners = counts.filter { it.value == max }
        return if (winners.size != 1) {
            null
        } else {
            winners.keys.first()
        }
    }


    private fun checkBallotValidity(b: Ballot<T>) {
        if (!candidates.containsAll(b.orderOfPreference)) {
            throw InvalidBallotException()
        }
    }
}