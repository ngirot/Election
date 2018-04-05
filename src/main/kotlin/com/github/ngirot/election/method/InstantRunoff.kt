package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot

object InstantRunoff {

    fun <T> scores(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        return t(ballots, emptyMap(), 1)
    }

    private tailrec fun <T> t(ballots: Sequence<Ballot<T>>, scores: Map<T, Int>, position: Int): Map<T, Int> {
        val size = ballots.map { it.selected() }
                .distinct()
                .count()

        if(size == 0) {
            return emptyMap()
        }

        val score = Borda.scores(ballots, size)

        val winner = winner(score, size)
        if(winner != null) {
            val ballotWithoutWinner = ballots.map { it.orderOfPreference.filter { it == winner } }
                    .map { Ballot(it)}

            val newScores = scores.entries
                    .union(mapOf(winner to position).entries)
                    .associate { it.key to it.value}

            return t(ballotWithoutWinner, newScores, position + 1)
        } else {
            val losers = losers(score)

            val ballotWihoutLosers = ballots.map { it.orderOfPreference.fil }
            // Remove loser and again
        }

        return ballots.first().positions().mapValues { size - it.value }
    }

    private fun <T> winner(scores: Map<T, Int>, threshold: Int): T? {
        val winner = scores.filterValues { it > threshold }
        return if(winner.size != 1) {
            null
        } else {
            winner.keys.first()
        }
    }

    private fun <T> losers(scores: Map<T, Int>): List<T> {
        val minValue = scores.map { it.value }.min()
        return if(minValue == null) {
            emptyList()
        } else {
            scores.filter { it.value == minValue }.map { it.key }
        }
    }
}