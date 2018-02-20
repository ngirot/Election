package com.github.ngirot.election

object Ranking {

    fun <T> byHigherScore(scores: Map<T, Int>): Map<T, Int> {
        return byScore(scores, { e -> e.value })
    }

    fun <T> byLowerScore(scores: Map<T, Int>): Map<T, Int> {
        return byScore(scores, { e -> -e.value })
    }

    private fun <T> byScore(scores: Map<T, Int>, orderSelector: (Map.Entry<T, Int>) -> Int): Map<T, Int> {
        val orderedScores = scores.entries.sortedBy { orderSelector(it) }

        var position = scores.size
        var lastScore: Int? = null
        var lastPosition = position

        return orderedScores.map { e ->
            var rank = position
            if (lastScore != null && lastScore == e.value) {
                rank = lastPosition
            }

            lastPosition = rank
            lastScore = e.value

            val pair = e.key to rank
            position--

            pair
        }.associate { it }
    }
}