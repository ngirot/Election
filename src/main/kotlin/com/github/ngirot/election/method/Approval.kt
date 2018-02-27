package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot

object Approval {

    fun <T> scores(ballots: Sequence<Ballot<T>>): Map<T, Int> {
        return ballots.flatMap { it.selected().asSequence() }
                .groupBy { it }
                .mapValues { it.value.size }
    }
}