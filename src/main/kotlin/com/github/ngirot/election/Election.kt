package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
import com.github.ngirot.election.method.Borda
import com.github.ngirot.election.method.Condorcet
import com.github.ngirot.election.method.FirstPastThePost
import com.github.ngirot.election.method.Random
import java.util.function.Function
import java.util.function.Supplier

class Election<T: Any>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val counter = Function<Sequence<Ballot<T>>, Map<T, Int>> { Condorcet.countLoss(it) }
        val scorer = Function<Map<T, Int>, Map<T, Int>> {Ranking.byLowerScore(it) }

        return voteWithBallot(counter, scorer).apply(votes)
    }

    fun firstPastThePost(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val counter = Function<Sequence<Ballot<T>>, Map<T, Int>> { FirstPastThePost.scores(it) }
        val scorer = Function<Map<T, Int>, Map<T, Int>> {Ranking.byHigherScore(it) }

        return voteWithBallot(counter, scorer).apply(votes)
    }

    fun borda(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val counter = Function<Sequence<Ballot<T>>, Map<T, Int>> { Borda.scores(it, candidates.size) }
        val scorer = Function<Map<T, Int>, Map<T, Int>> { Ranking.byHigherScore(it) }

        return voteWithBallot(counter, scorer).apply(votes)
    }


    fun random(): ElectionResult<T> {
        val counter = Supplier { Random.scores(candidates) }
        val scorer = Function<Map<T, Int>, Map<T, Int>> { Ranking.byHigherScore(it) }

        return voteWithoutBallot(counter, scorer).get()
    }

    private fun voteWithBallot(counter: Function<Sequence<Ballot<T>>, Map<T, Int>>, scorer: Function<Map<T, Int>, Map<T, Int>>): Function<Sequence<Ballot<T>>, ElectionResult<T>> {
        val ballotChecker = Function<Sequence<Ballot<T>>, Sequence<Ballot<T>>> { it.map { ensureBallotValidity(it) } }

        return resultBuilder(scorer)
                .compose(counter)
                .compose(ballotChecker)
    }

    private fun voteWithoutBallot(counter: Supplier<Map<T, Int>>, scorer: Function<Map<T, Int>, Map<T, Int>>): Supplier<ElectionResult<T>> {
        return Supplier { resultBuilder(scorer).apply(counter.get()) }
    }

    private fun resultBuilder(scorer: Function<Map<T, Int>, Map<T, Int>>): Function<Map<T, Int>, ElectionResult<T>> {
        val buildElectionResult = Function<Map<T, Int>, ElectionResult<T>> { ElectionResult(it) }

        return buildElectionResult.compose(scorer)
    }

    private fun ensureBallotValidity(b: Ballot<T>):  Ballot<T> {
        if (!candidates.containsAll(b.orderOfPreference)) {
            throw InvalidBallotException()
        }
        return b
    }
}