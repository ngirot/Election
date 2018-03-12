package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot

object InstantRunoff {

    fun <T> scores(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        val size = ballots.map { it.orderOfPreference }.distinct().count()
        if(size == 0) {
            return emptyMap()
        }

        return ballots.first().positions().mapValues { size - it.value }
    }
}