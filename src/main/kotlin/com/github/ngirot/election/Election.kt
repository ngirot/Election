package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
import com.github.ngirot.election.method.Condorcet
import com.github.ngirot.election.method.FirstPastThePost

class Election<T: Any>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): T? {
        val losses = Condorcet.countLoss(votes, this::checkBallotValidity)

        val winners = losses.filter { it.value == 0 }
                .map { it.key }

        return if (winners.size == 1) {
            winners.first()
        } else {
            null
        }
    }

    fun firstPastThePost(votes: Sequence<Ballot<T>>): T? {
        val counts = FirstPastThePost.score(votes, this::checkBallotValidity)

        val max = counts.map { it.value }.max()
        val winners = counts.filter { it.value == max }

        return if (winners.size == 1) {
            winners.keys.first()
        } else {
            null
        }
    }

    private fun checkBallotValidity(b: Ballot<T>) {
        if (!candidates.containsAll(b.orderOfPreference)) {
            throw InvalidBallotException()
        }
    }
}