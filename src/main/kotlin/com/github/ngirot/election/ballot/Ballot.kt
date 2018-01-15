package com.github.ngirot.election.ballot

data class Ballot<out T> constructor(val orderOfPreference: List<T>) {

    fun extractLinks(): Sequence<Pair<T, T>> {
        val links = mutableListOf<Pair<T, T>>()

        val preferenceSize = orderOfPreference.size

        for(i in 0 until preferenceSize) {
            for (j in i+1 until preferenceSize) {
                links.add(Pair(orderOfPreference[i], orderOfPreference[j]))
            }

        }

        return links.asSequence()
    }
}