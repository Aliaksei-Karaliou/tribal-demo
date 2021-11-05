package com.aliakseikaraliou.github.tribaldemo.credit.model

import java.io.Serializable
import java.math.BigDecimal
import java.time.Instant

data class CreditLine(
    val business: Business,
    val requestedCreditLine: BigDecimal,
    val requestedDate: Instant
) : Serializable