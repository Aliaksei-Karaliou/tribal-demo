package com.aliakseikaraliou.github.tribaldemo.credit.extensions

import org.mockito.Mockito.any

fun <T> anyObject(): T {
    any<T>()
    return uninitialized()
}

@Suppress("UNCHECKED_CAST")
private fun <T> uninitialized(): T = null as T