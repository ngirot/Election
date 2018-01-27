package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
import com.github.ngirot.election.method.Condorcet
import com.github.ngirot.election.method.FirstPastThePost

class Election<T: Any>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val losses = Condorcet.countLoss(votes, this::checkBallotValidity)
        val ranking = Ranking.byLowerScore(losses)
        return ElectionResult(ranking)
    }

    fun firstPastThePost(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val counts = FirstPastThePost.score(votes, this::checkBallotValidity)
        val ranking = Ranking.byHigherScore(counts)
        return ElectionResult(ranking)
    }

    private fun checkBallotValidity(b: Ballot<T>) {
        if (!candidates.containsAll(b.orderOfPreference)) {
            throw InvalidBallotException()
        }
    }
}