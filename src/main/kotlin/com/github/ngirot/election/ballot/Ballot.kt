package com.github.ngirot.election.ballot

data class Ballot<T> constructor(val orderOfPreference: List<T>) {

    fun extractDuelsWon(): List<Pair<T, T>> {
        val links = mutableListOf<Pair<T, T>>()

        val preferenceSize = orderOfPreference.size

        for(i in 0 until preferenceSize) {
            for (j in i+1 until preferenceSize) {
                links.add(orderOfPreference[i] to orderOfPreference[j])
            }

        }

        return links
    }

    fun positions(): Map<T, Int> {
        return orderOfPreference.mapIndexed { pos, item -> item to pos +1 }
                .associate { it }
    }

    fun first(): T? {
        return orderOfPreference.firstOrNull()
    }
}