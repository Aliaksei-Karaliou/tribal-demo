package com.aliakseikaraliou.github.tribaldemo.credit.strategy

import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessType.SME
import com.aliakseikaraliou.github.tribaldemo.extensions.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class SMERecommendedCreditLineStrategy(
    @Value("\${app.credit.sme.monthly_ratio:0.2}")
    private val monthlyRevenueRatio: BigDecimal
) : RecommendedCreditLineStrategy {

    override val businessType = SME

    override fun maxRecommendedCreditLine(moneyFlow: BusinessMoneyFlow): BigDecimal {
        val result = moneyFlow.monthlyRevenue * monthlyRevenueRatio

        LOGGER.debug("Maximum calculated credit line for $moneyFlow is $result (SME)")

        return result
    }

    companion object {
        private val LOGGER = getLogger<SMERecommendedCreditLineStrategy>()
    }
}