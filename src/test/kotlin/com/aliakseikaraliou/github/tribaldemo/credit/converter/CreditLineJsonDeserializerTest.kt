package com.aliakseikaraliou.github.tribaldemo.credit.converter

import com.aliakseikaraliou.github.tribaldemo.credit.exception.IncorrectInputDataException
import com.aliakseikaraliou.github.tribaldemo.credit.model.Business
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessMoneyFlow
import com.aliakseikaraliou.github.tribaldemo.credit.model.BusinessType.SME
import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditLine
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.concurrent.TimeUnit.MILLISECONDS

class CreditLineJsonDeserializerTest {

    private lateinit var creditLineJsonDeserializer: CreditLineJsonDeserializer

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        creditLineJsonDeserializer = CreditLineJsonDeserializer()

        val module = SimpleModule()
            .addDeserializer(CreditLine::class.java, creditLineJsonDeserializer)

        objectMapper = ObjectMapper()
            .apply {
                registerModule(module)
            }
    }

    @Test
    fun positiveTest() {
        val expected = CreditLine(
            business = Business(
                type = SME,
                moneyFlow = BusinessMoneyFlow(
                    cashBalance = BigDecimal(435.30),
                    monthlyRevenue = BigDecimal(4235.45)
                )
            ),
            requestedCreditLine = BigDecimal(100.0),
            requestedDate = LocalDateTime.of(2021, 7, 19, 16, 32, 59, MILLISECONDS.toNanos(860).toInt())
                .toInstant(UTC)
        )
        val actual = objectMapper
            .readValue(
                """
                    {
                       "foundingType":"SME",
                       "cashBalance":435.30,
                       "monthlyRevenue":4235.45,
                       "requestedCreditLine":100,
                       "requestedDate":"2021-07-19T16:32:59.860Z"
                    }
                """,
                CreditLine::class.java
            )

        assertEquals(expected, actual)
    }

    @Test
    fun negativeCashBalanceTest() {
        val exception = assertThrows<IncorrectInputDataException>("Cash balance cannot be negative") {
            objectMapper
                .readValue(
                    """
                    {
                       "foundingType":"SME",
                       "cashBalance":-435.30,
                       "monthlyRevenue":4235.45,
                       "requestedCreditLine":100,
                       "requestedDate":"2021-07-19T16:32:59.860Z"
                    }
                """,
                    CreditLine::class.java
                )
        }
        assertEquals("Cash balance cannot be negative", exception.message)
    }

    @Test
    fun negativeMonthlyRevenueTest() {
        val exception = assertThrows<IncorrectInputDataException>("Monthly revenue cannot be negative") {
            objectMapper
                .readValue(
                    """
                    {
                       "foundingType":"SME",
                       "cashBalance":435.30,
                       "monthlyRevenue":-4235.45,
                       "requestedCreditLine":100,
                       "requestedDate":"2021-07-19T16:32:59.860Z"
                    }
                """,
                    CreditLine::class.java
                )
        }
        assertEquals("Monthly revenue cannot be negative", exception.message)
    }

    @Test
    fun negativeRequestedCreditLineTest() {
        val exception = assertThrows<IncorrectInputDataException>("Requested credit line cannot be negative") {
            objectMapper
                .readValue(
                    """
                    {
                       "foundingType":"SME",
                       "cashBalance":435.30,
                       "monthlyRevenue":4235.45,
                       "requestedCreditLine":-100,
                       "requestedDate":"2021-07-19T16:32:59.860Z"
                    }
                """,
                    CreditLine::class.java
                )
        }
        assertEquals("Requested credit line cannot be negative", exception.message)
    }
}