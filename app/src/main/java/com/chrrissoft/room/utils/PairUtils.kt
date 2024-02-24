package com.chrrissoft.room.utils

object PairUtils {
    fun <A, B> Pair<A, B>.mapSecond(block: B.() -> B): Pair<A, B> {
        return copy(first, block(second))
    }
}
