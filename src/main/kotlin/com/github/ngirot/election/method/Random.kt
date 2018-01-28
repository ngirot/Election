package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot

object Random {

    fun <T> scores(candidates: List<T>): Map<T, Int> {
        return candidates.shuffled()
                .mapIndexed { pos, item -> item to pos +1 }
                .associate { it }
    }
}