package com.aliakseikaraliou.github.tribaldemo.credit.model

import java.io.Serializable
import java.math.BigDecimal

data class BusinessMoneyFlow(
    val cashBalance: BigDecimal,
    val monthlyRevenue: BigDecimal
) : Serializable