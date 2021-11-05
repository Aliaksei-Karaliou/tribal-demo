package com.aliakseikaraliou.github.tribaldemo.credit.converter

import com.aliakseikaraliou.github.tribaldemo.credit.exception.IncorrectInputDataException
import com.aliakseikaraliou.github.tribaldemo.credit.model.Business
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessType
import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditLine
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.node.NumericNode
import com.fasterxml.jackson.databind.node.TextNode
import org.springframework.boot.jackson.JsonComponent
import java.math.BigDecimal
import java.time.Instant
import java.time.format.DateTimeFormatter

@JsonComponent
class CreditLineJsonDeserializer : JsonDeserializer<CreditLine>() {
    private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    override fun deserialize(
        jsonParser: JsonParser,
        deserializationContext: DeserializationContext
    ): CreditLine {
        val treeNode = jsonParser.codec.readTree<TreeNode>(jsonParser)

        val businessType = BusinessType.valueOf(
            (treeNode.get("foundingType") as TextNode).textValue()
        )
        val cashBalance = (treeNode.get("cashBalance") as NumericNode).doubleValue()

        if (cashBalance < 0) {
            throw IncorrectInputDataException("Cash balance cannot be negative")
        }

        val monthlyRevenue = (treeNode.get("monthlyRevenue") as NumericNode).doubleValue()

        if (monthlyRevenue < 0) {
            throw IncorrectInputDataException("Monthly revenue cannot be negative")
        }

        val requestedCreditLine = (treeNode.get("requestedCreditLine") as NumericNode).doubleValue()

        if (requestedCreditLine < 0) {
            throw IncorrectInputDataException("Requested credit line cannot be negative")
        }

        val requestedDate = dateTimeFormatter.parse(
            (treeNode.get("requestedDate") as TextNode).textValue(),
            Instant::from
        )

        if (requestedDate.isAfter(Instant.now())) {
            throw IncorrectInputDataException("Requested date is in future")
        }

        return CreditLine(
            business = Business(
                type = businessType,
                moneyFlow = BusinessMoneyFlow(
                    monthlyRevenue = BigDecimal(monthlyRevenue),
                    cashBalance = BigDecimal(cashBalance)
                )
            ),
            requestedCreditLine = BigDecimal(requestedCreditLine),
            requestedDate = requestedDate
        )
    }
}