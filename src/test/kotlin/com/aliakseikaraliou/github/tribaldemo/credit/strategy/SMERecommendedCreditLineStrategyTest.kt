package com.aliakseikaraliou.github.tribaldemo.credit.strategy

import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class SMERecommendedCreditLineStrategyTest {

    private lateinit var recommendedCreditLineStrategy: SMERecommendedCreditLineStrategy

    @BeforeEach
    fun setUp() {
        recommendedCreditLineStrategy = SMERecommendedCreditLineStrategy(
            monthlyRevenueRatio = BigDecimal(0.2)
        )
    }

    @Test
    fun smeCreditLine1Test() {
        val business = BusinessMoneyFlow(BigDecimal(1000.0), BigDecimal(100.0))

        assertEquals(20.00, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }

    @Test
    fun smeCreditLine2Test() {
        val business = BusinessMoneyFlow(BigDecimal(34.0), BigDecimal(87.0))

        assertEquals(17.40, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }

    @Test
    fun smeCreditLine3Test() {
        val business = BusinessMoneyFlow(BigDecimal(11290.0), BigDecimal(11.0))

        assertEquals(2.20, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }

    @Test
    fun smeCreditLine4Test() {
        val business = BusinessMoneyFlow(BigDecimal(100.0), BigDecimal(11290.0))

        assertEquals(2258.00, recommendedCreditLineStrategy.maxRecommendedCreditLine(business).toDouble(), 0.01)
    }
}