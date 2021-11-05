package com.aliakseikaraliou.github.tribaldemo.extensions

import java.net.InetAddress
import javax.servlet.http.HttpServletRequest

val HttpServletRequest.clientIp: InetAddress
    get() {
        val address = (getHeader("X-Forwarded-For")
            ?.split(",")
            ?.getOrNull(0)
            ?: remoteAddr)

        return InetAddress.getByName(address)
    }