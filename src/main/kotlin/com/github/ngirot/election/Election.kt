package com.github.ngirot.election

import com.github.ngirot.election.ballot.Ballot
import com.github.ngirot.election.ballot.InvalidBallotException
import com.github.ngirot.election.method.Borda
import com.github.ngirot.election.method.Condorcet
import com.github.ngirot.election.method.FirstPastThePost
import com.github.ngirot.election.method.Sortition

class Election<T: Any>(private val candidates: List<T>) {

    fun condorcet(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        return voteWithBallot(Condorcet::countLoss, Ranking::byLowerScore)(votes)
    }

    fun firstPastThePost(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        return voteWithBallot(FirstPastThePost::scores, Ranking::byHigherScore)(votes)
    }

    fun borda(votes: Sequence<Ballot<T>>): ElectionResult<T> {
        val scorer: (Sequence<Ballot<T>>) -> Map<T, Int> = { Borda.scores(it, candidates.size) }
        return voteWithBallot(scorer, Ranking::byHigherScore)(votes)
    }

    fun sortition(): ElectionResult<T> {
        val scorer = { Sortition.scores(candidates) }
        return voteWithoutBallot(scorer, Ranking::byHigherScore)()
    }

    private fun voteWithBallot(counter: (Sequence<Ballot<T>>) -> Map<T, Int>, scorer: (Map<T, Int>) -> Map<T, Int>): (Sequence<Ballot<T>>) -> ElectionResult<T> {
        val ballotChecker: (b: Sequence<Ballot<T>>) -> Sequence<Ballot<T>> = { it.map { ensureBallotValidity(it) } }


        return resultBuilder(scorer)
                .compose(counter)
                .compose(ballotChecker)
    }

    private fun voteWithoutBallot(counter: () -> Map<T, Int>, scorer: (Map<T, Int>) ->  Map<T, Int>): () -> ElectionResult<T> {
        return { resultBuilder(scorer)(counter()) }
    }

    private fun resultBuilder(scorer: (Map<T, Int>) -> Map<T, Int>): (Map<T, Int>) -> ElectionResult<T> {
        val buildElectionResult = { a: Map<T, Int> -> ElectionResult(a) }

        return buildElectionResult.compose(scorer)
    }

    private fun ensureBallotValidity(b: Ballot<T>):  Ballot<T> {
        if (!candidates.containsAll(b.orderOfPreference)) {
            throw InvalidBallotException()
        }
        return b
    }
}