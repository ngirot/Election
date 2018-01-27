package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
import com.github.ngirot.election.method.Condorcet
import com.github.ngirot.election.method.FirstPastThePost
import java.util.function.Function

class Election<T: Any>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val counter = Function<Sequence<Ballot<T>>, Map<T, Int>> { Condorcet.countLoss(it) }
        val scorer = Function<Map<T, Int>, Map<T, Int>> {Ranking.byLowerScore(it) }

        return vote(counter, scorer).apply(votes)
    }

    fun firstPastThePost(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val counter = Function<Sequence<Ballot<T>>, Map<T, Int>> { FirstPastThePost.score(it) }
        val scorer = Function<Map<T, Int>, Map<T, Int>> {Ranking.byHigherScore(it) }

        return vote(counter, scorer).apply(votes)
    }

    private fun vote(counter: Function<Sequence<Ballot<T>>, Map<T, Int>>, scorer: Function<Map<T, Int>, Map<T, Int>>): Function<Sequence<Ballot<T>>, ElectionResult<T>> {
        val buildElectionResult = Function<Map<T, Int>, ElectionResult<T>> { ElectionResult(it) }
        val ballotChecker = Function<Sequence<Ballot<T>>, Sequence<Ballot<T>>> { it.map { ensureBallotValidity(it) } }

        return buildElectionResult
                .compose(scorer)
                .compose(counter)
                .compose(ballotChecker)
    }

    private fun ensureBallotValidity(b: Ballot<T>):  Ballot<T> {
        if (!candidates.containsAll(b.orderOfPreference)) {
            throw InvalidBallotException()
        }
        return b
    }
}