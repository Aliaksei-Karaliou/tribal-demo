package com.aliakseikaraliou.github.tribaldemo.credit.strategy

import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessType.STARTUP
import com.aliakseikaraliou.github.tribaldemo.extensions.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class StartupRecommendedCreditLineStrategy(
    @Value("\${app.credit.startup.cash_balance_ratio:0.3333333333333333}")
    private val cashBalanceRatio: BigDecimal,

    @Value("\${app.credit.startup.monthly_ratio:0.2}")
    private val monthlyRevenueRatio: BigDecimal
) : RecommendedCreditLineStrategy {
    override val businessType = STARTUP

    override fun maxRecommendedCreditLine(moneyFlow: BusinessMoneyFlow): BigDecimal {
        val cashBalanceRatio = moneyFlow.cashBalance * cashBalanceRatio
        val monthlyRevenueCreditLine = moneyFlow.monthlyRevenue * monthlyRevenueRatio

        val result = cashBalanceRatio.max(monthlyRevenueCreditLine)

        LOGGER.debug("Maximum calculated credit line for $moneyFlow is $result (Startup)")

        return result
    }

    companion object {
        private val LOGGER = getLogger<StartupRecommendedCreditLineStrategy>()
    }
}