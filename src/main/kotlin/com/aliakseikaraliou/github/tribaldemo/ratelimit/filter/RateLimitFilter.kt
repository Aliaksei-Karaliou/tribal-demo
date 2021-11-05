package com.aliakseikaraliou.github.tribaldemo.ratelimit.filter

import com.aliakseikaraliou.github.tribaldemo.extensions.clientIp
import com.aliakseikaraliou.github.tribaldemo.ratelimit.exception.SalesAgentContactRequiredException
import com.aliakseikaraliou.github.tribaldemo.ratelimit.service.RateLimitService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter("/credit")
class RateLimitFilter(
    private val rateLimitService: RateLimitService
) : Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpServletRequest = request as HttpServletRequest
        val httpServletResponse = response as HttpServletResponse

        val clientIp = httpServletRequest.clientIp

        try {
            if (!rateLimitService.canBeConsumed(clientIp)) {
                httpServletResponse.sendError(TOO_MANY_REQUESTS.value())
                return
            }

            chain.doFilter(request, response)

            val isSucceed = httpServletResponse.status == HttpStatus.OK.value()
            rateLimitService.consume(clientIp, isSucceed)
        } catch (e: SalesAgentContactRequiredException) {
            httpServletResponse.sendError(TOO_MANY_REQUESTS.value(), "A sales agent will contact you")
        }

    }
}