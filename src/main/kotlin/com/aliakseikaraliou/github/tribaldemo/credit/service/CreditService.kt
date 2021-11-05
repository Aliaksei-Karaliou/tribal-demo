package com.aliakseikaraliou.github.tribaldemo.credit.service

import com.aliakseikaraliou.github.tribaldemo.credit.exception.StrategyNotFoundException
import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditApproveResult
import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditLine
import com.aliakseikaraliou.github.tribaldemo.credit.strategy.RecommendedCreditLineStrategy
import org.springframework.stereotype.Service

@Service
class CreditService(private val creditLineStrategies: List<RecommendedCreditLineStrategy>) {

    fun applyForCredit(line: CreditLine): CreditApproveResult {
        val maxRecommendedCreditLine = creditLineStrategies
            .find { it.businessType == line.business.type }
            ?.maxRecommendedCreditLine(line.business.moneyFlow)
            ?: throw StrategyNotFoundException("Not found credit line strategy for business type ${line.business.type}")

        return CreditApproveResult(
            isApproved = line.requestedCreditLine < maxRecommendedCreditLine,
            requestedCreditLine = line.requestedCreditLine
        )
    }
}