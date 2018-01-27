package com.github.ngirot.election.method

import com.github.ngirot.election.ballot.Ballot

object Borda {

    fun <T> scores(ballots : Sequence<Ballot<T>>, scoreMax: Int): Map<T, Int> {
        return ballots.map { it.positions() }
                .flatMap { it.entries.asSequence() }
                .map { it.key to scoreMax - it.value + 1 }
                .groupBy { it.first }
                .mapValues { it.value.map { it.second } }
                .mapValues { it.value.reduce { a, b -> a + b } }
                .mapValues { it.value }
    }
}