package com.aliakseikaraliou.github.tribaldemo.credit.model

import java.io.Serializable
import java.math.BigDecimal

class CreditApproveResult(
    val isApproved: Boolean,
    val requestedCreditLine: BigDecimal
) : Serializable