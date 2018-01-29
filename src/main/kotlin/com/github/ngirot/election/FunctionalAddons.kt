package com.github.ngirot.election

infix fun <T, U, V> ((T) -> U).compose(f: (V) -> T): (V) -> U  = { this(f(it)) }