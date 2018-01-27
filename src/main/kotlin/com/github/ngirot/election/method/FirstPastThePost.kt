package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot

object FirstPastThePost {

    fun <T: Any> score(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        return ballots.map { it.first() }
                .filterNotNull()
                .groupBy { it }
                .mapValues { it.value.size }
    }
}