package com.aliakseikaraliou.github.tribaldemo.extensions

import io.github.bucket4j.Bucket

fun Bucket.canBeConsumed(numTokens: Long = 1): Boolean {
    val result = tryConsume(numTokens)

    if (result) {
        addTokens(numTokens)
    }

    return result
}