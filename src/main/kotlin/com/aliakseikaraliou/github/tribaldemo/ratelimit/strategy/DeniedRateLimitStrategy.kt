package com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy

import com.aliakseikaraliou.github.tribaldemo.extensions.canBeConsumed
import com.aliakseikaraliou.github.tribaldemo.extensions.getLogger
import com.aliakseikaraliou.github.tribaldemo.ratelimit.exception.SalesAgentContactRequiredException
import com.aliakseikaraliou.github.tribaldemo.ratelimit.storage.RateLimitStorage
import java.net.InetAddress

class DeniedRateLimitStrategy(
    private val storage: RateLimitStorage,
    private val requestsToSales: Long
) : RateLimitStrategy {
    private val waitingForSalesAgentMap: MutableMap<InetAddress, Int> = mutableMapOf()

    override fun canBeConsumed(inetAddress: InetAddress): Boolean {
        val canBeConsumed = storage.obtainBucket(inetAddress).canBeConsumed()

        if (!canBeConsumed) {
            val currentErrors = waitingForSalesAgentMap.getOrDefault(inetAddress, 0)
            waitingForSalesAgentMap[inetAddress] = currentErrors + 1
        }

        verifyInetAddressForSalesAgent(inetAddress)

        return canBeConsumed
    }

    override fun consume(inetAddress: InetAddress): Boolean {
        LOGGER.debug("${inetAddress.hostAddress} consumed as denied")
        verifyInetAddressForSalesAgent(inetAddress)

        return storage.obtainBucket(inetAddress).tryConsume(1)
    }

    private fun verifyInetAddressForSalesAgent(inetAddress: InetAddress) {
        if (waitingForSalesAgentMap.getOrDefault(inetAddress, 0) >= requestsToSales) {
            throw SalesAgentContactRequiredException()
        }
    }

    companion object {
        private val LOGGER = getLogger<DeniedRateLimitStrategy>()
    }

}