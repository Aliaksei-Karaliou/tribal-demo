package com.aliakseikaraliou.github.tribaldemo.ratelimit.service

import com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy.ApprovedRateLimitStrategy
import com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy.DeniedRateLimitStrategy
import org.springframework.stereotype.Service
import java.net.InetAddress

@Service
class RateLimitService(
    private val approvedRateLimitStrategy: ApprovedRateLimitStrategy,
    private val deniedRateLimitStrategy: DeniedRateLimitStrategy
) {
    fun canBeConsumed(inetAddress: InetAddress): Boolean =
        approvedRateLimitStrategy.canBeConsumed(inetAddress) && deniedRateLimitStrategy.canBeConsumed(inetAddress)

    fun consume(inetAddress: InetAddress, isSucceed: Boolean): Boolean = if (isSucceed) {
        approvedRateLimitStrategy.consume(inetAddress)
    } else {
        deniedRateLimitStrategy.consume(inetAddress)
    }
}