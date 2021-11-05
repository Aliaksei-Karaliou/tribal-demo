package com.aliakseikaraliou.github.tribaldemo.credit.strategy

import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessType
import java.math.BigDecimal

interface RecommendedCreditLineStrategy {
    val businessType: BusinessType

    fun maxRecommendedCreditLine(moneyFlow: BusinessMoneyFlow): BigDecimal
}