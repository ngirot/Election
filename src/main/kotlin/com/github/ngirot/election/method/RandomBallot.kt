package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import java.security.SecureRandom

object RandomBallot {

    fun <T : Any> scores(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        val scores = mutableMapOf<T, Int>()

        rec(ballots.toList(), scores, 1)

        val positionMax = scores.map { it.value }.max() ?: 0

        return scores.mapValues { positionMax - it.value }
    }

    fun <T> pickOne(ballots: List<Ballot<T>>): T? {
        return ballots.shuffled(SecureRandom()).firstOrNull()?.first()
    }

    tailrec fun <T> rec(remaining: List<Ballot<T>>, scores: MutableMap<T, Int>, position: Int) {
        val picked = pickOne(remaining)
        if (picked == null) {
            return
        } else
            scores[picked] = position
            rec(remaining.filter { it.first() != picked }, scores, position+1)
    }
}