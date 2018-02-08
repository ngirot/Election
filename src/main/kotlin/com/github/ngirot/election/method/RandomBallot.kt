package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot
import java.security.SecureRandom

object RandomBallot {

    fun <T : Any> scores(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        val scores = mutableMapOf<T, Int>()

        val nbOfCandidates = ballots.map { it.first() }
                .filterNotNull()
                .count()

        var restrictedBallots = ballots.toList()
        var ended = false
        var score = nbOfCandidates

        while (!ended) {
            val elected = pickOne(restrictedBallots)


            if (elected == null) {
                ended = true
            } else {
                scores[elected] = score--
            }
        }

        return scores
    }

    fun <T> pickOne(ballots: List<Ballot<T>>): T? {
        return ballots.shuffled(SecureRandom()).firstOrNull()?.first()
    }
}