package com.github.ngirot.election.graph

data class Node<out T> constructor(val from: T, val to: T, var weight: Long  = 0) {
    fun increase() {
        weight++
    }

    fun decrease() {
        weight--
    }
}