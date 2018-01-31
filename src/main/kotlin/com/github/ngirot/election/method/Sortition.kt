package com.github.ngirot.election.method

import java.security.SecureRandom

object Sortition {

    fun <T> scores(candidates: List<T>): Map<T, Int> {
        return candidates.shuffled(SecureRandom())
                .mapIndexed { pos, item -> item to pos + 1 }
                .associate { it }
    }
}