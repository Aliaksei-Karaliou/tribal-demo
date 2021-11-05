package com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy

import java.net.InetAddress

interface RateLimitStrategy {
    fun canBeConsumed(inetAddress: InetAddress): Boolean

    fun consume(inetAddress: InetAddress): Boolean
}