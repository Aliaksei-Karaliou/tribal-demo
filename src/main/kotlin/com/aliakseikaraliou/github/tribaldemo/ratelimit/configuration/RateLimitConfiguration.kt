package com.aliakseikaraliou.github.tribaldemo.ratelimit.configuration

import com.aliakseikaraliou.github.tribaldemo.ratelimit.storage.RateLimitStorage
import com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy.ApprovedRateLimitStrategy
import com.aliakseikaraliou.github.tribaldemo.ratelimit.strategy.DeniedRateLimitStrategy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration.ofSeconds

@Configuration
class RateLimitConfiguration {

    @Bean
    fun approvedRateLimitStrategy(
        @Value("\${app.rate_limit.approved.capacity:2}") capacity: Int,
        @Value("\${app.rate_limit.approved.duration:120}") duration: Long,
        @Value("\${app.rate_limit.approved.cache_expiration:600}") cacheExpiration: Long,
    ) = ApprovedRateLimitStrategy(
        storage = RateLimitStorage(
            capacity = capacity,
            duration = ofSeconds(duration),
            cacheExpiration = ofSeconds(cacheExpiration)
        )
    )

    @Bean
    fun deniedRateLimitStrategy(
        @Value("\${app.rate_limit.denied.capacity:1}") capacity: Int,
        @Value("\${app.rate_limit.denied.duration:30}") duration: Long,
        @Value("\${app.rate_limit.denied.cache_expiration:600}") cacheExpiration: Long,
        @Value("\${app.rate_limit.denied.request_to_sales_count:30}") requestsToSales: Long,
    ) = DeniedRateLimitStrategy(
        storage = RateLimitStorage(
            capacity = capacity,
            duration = ofSeconds(duration),
            cacheExpiration = ofSeconds(cacheExpiration)
        ),
        requestsToSales = requestsToSales
    )
}