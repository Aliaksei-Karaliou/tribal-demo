package com.aliakseikaraliou.github.tribaldemo.ratelimit.storage

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import io.github.bucket4j.Bandwidth.classic
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill.intervally
import java.net.InetAddress
import java.time.Duration

class RateLimitStorage(
    private val capacity: Int,
    private val duration: Duration,
    cacheExpiration: Duration
) {
    private val cache: Cache<InetAddress, Bucket> = Caffeine.newBuilder()
        .expireAfterAccess(cacheExpiration)
        .build()

    private val bucket: Bucket
        get() = Bucket4j
            .builder()
            .addLimit(
                classic(
                    capacity.toLong(),
                    intervally(capacity.toLong(), duration)
                )
            )
            .build()

    fun obtainBucket(inetAddress: InetAddress): Bucket = cache.get(inetAddress) { bucket }
}