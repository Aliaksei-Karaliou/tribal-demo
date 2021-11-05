package com.aliakseikaraliou.github.tribaldemo.credit.strategy

import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class StartupRecommendedCreditLineStrategyTest {

    private lateinit var recommendedCreditLineStrategy: StartupRecommendedCreditLineStrategy

    @BeforeEach
    fun setUp() {
        recommendedCreditLineStrategy = StartupRecommendedCreditLineStrategy(
            cashBalanceRatio = BigDecimal(1.0 / 3),
            monthlyRevenueRatio = BigDecimal(0.2)
        )
    }

    @Test
    fun startupCreditLine1Test() {
        val business = BusinessMoneyFlow(BigDecimal(1000.0), BigDecimal(100.0))

        assertEquals(333.33, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }

    @Test
    fun startupCreditLine2Test() {
        val business = BusinessMoneyFlow(BigDecimal(34.0), BigDecimal(87.0))

        assertEquals(17.40, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }

    @Test
    fun startupCreditLine3Test() {
        val business = BusinessMoneyFlow(BigDecimal(11290.0), BigDecimal(11.0))

        assertEquals(3763.33, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }

    @Test
    fun startupCreditLine4Test() {
        val business = BusinessMoneyFlow(BigDecimal(100.0), BigDecimal(11290.0))

        assertEquals(2258.00, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }
}