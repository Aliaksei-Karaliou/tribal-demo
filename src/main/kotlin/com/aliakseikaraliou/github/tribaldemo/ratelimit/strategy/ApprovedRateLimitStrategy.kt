package com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy

import com.aliakseikaraliou.github.tribaldemo.extensions.canBeConsumed
import com.aliakseikaraliou.github.tribaldemo.extensions.getLogger
import com.aliakseikaraliou.github.tribaldemo.ratelimit.storage.RateLimitStorage
import java.net.InetAddress

class ApprovedRateLimitStrategy(private val storage: RateLimitStorage) : RateLimitStrategy {
    override fun canBeConsumed(inetAddress: InetAddress): Boolean = storage.obtainBucket(inetAddress).canBeConsumed()

    override fun consume(inetAddress: InetAddress): Boolean {
        LOGGER.debug("${inetAddress.hostAddress} consumed as approved")

        return storage.obtainBucket(inetAddress).tryConsume(1)
    }

    companion object {
        private val LOGGER = getLogger<ApprovedRateLimitStrategy>()
    }
}