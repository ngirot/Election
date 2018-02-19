package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import java.security.SecureRandom

object RandomBallot {

    fun <T> scores(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        val scores = positions(ballots.toList(), mapOf(), 1)

        val positionMax = scores.map { it.value }.max() ?: 0

        return scores.mapValues { positionMax - it.value }
    }

    private tailrec fun <T> positions(remaining: List<Ballot<T>>, scores: Map<T, Int>, position: Int): Map<T, Int> {
        val picked = pickOne(remaining)

        return if (picked != null) {
            val newScores = scores.entries
                    .union(mapOf(picked to position).entries)
                    .associate { it.key to it.value}

            positions(remaining.filter { it.first() != picked }, newScores, position + 1)
        } else {
            scores
        }
    }

    private fun <T> pickOne(ballots: List<Ballot<T>>): T? {
        return ballots.shuffled(SecureRandom()).firstOrNull()?.first()
    }
}