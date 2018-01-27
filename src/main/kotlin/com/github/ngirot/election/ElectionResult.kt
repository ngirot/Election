package com.github.ngirot.election

class ElectionResult<out T>(private val ranking: Map<T, Int>) {

    fun winner(): T? {
        val winners = ranking.filter { it.value == 1 }

        return if (winners.size == 1) {
            winners.entries.first().key
        } else {
            null
        }
    }
}