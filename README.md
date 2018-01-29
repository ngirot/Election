# Election [![Build Status](https://travis-ci.org/ngirot/Election.svg?branch=master)](https://travis-ci.org/ngirot/Election)
A Kotlin lib implementing some majors electoral systems

## Systems implemented

### Condorcet
The winner of condorcet is the candidate who wins each head-o-head election: so each voter order candidates on a ballot giving us the head-to-head scores.

More details on https://en.wikipedia.org/wiki/Condorcet_method

### First-past-the-post
Each voter chose one candidate on a ballot, and the one having the most ballot win.

More details on https://en.wikipedia.org/wiki/First-past-the-post_voting

### Borda count
Each voter sort all candidates on a ballot, giving them n-position (where n is the number of candidates) points.
The candidate with the most points wins.

More details on https://en.wikipedia.org/wiki/Borda_count

### Sortition
The Athenian electoral system: a candidate is choosen randomly 

More details on https://en.wikipedia.org/wiki/Sortition
