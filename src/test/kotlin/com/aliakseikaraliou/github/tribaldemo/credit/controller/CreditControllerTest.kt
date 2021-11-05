package com.aliakseikaraliou.github.tribaldemo.credit.controller

import com.aliakseikaraliou.github.tribaldemo.credit.extensions.anyObject
import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditApproveResult
import com.aliakseikaraliou.github.tribaldemo.credit.service.CreditService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(CreditController::class)
class CreditControllerTest {
    @MockBean
    private lateinit var creditService: CreditService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testPositive() {
        `when`(creditService.applyForCredit(anyObject()))
            .thenReturn(CreditApproveResult(true, BigDecimal(100.0)))
        val input = """
                          {
                       "foundingType":"SME",
                       "cashBalance":435.30,
                       "monthlyRevenue":4235.45,
                       "requestedCreditLine":100,
                       "requestedDate":"2021-07-19T16:32:59.860Z"
                    }
        """.trimIndent()

        mockMvc.perform(
            post("/credit/")
                .contentType(APPLICATION_JSON)
                .content(input)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.isApproved").value(true))
            .andExpect(jsonPath("$.requestedCreditLine").value(100.0))
    }

    @Test
    fun testNegative() {
        `when`(creditService.applyForCredit(anyObject()))
            .thenReturn(CreditApproveResult(false, BigDecimal(100.0)))
        val input = """
                          {
                       "foundingType":"SME",
                       "cashBalance":435.30,
                       "monthlyRevenue":4235.45,
                       "requestedCreditLine":100,
                       "requestedDate":"2021-07-19T16:32:59.860Z"
                    }
        """.trimIndent()

        mockMvc.perform(
            post("/credit/")
                .contentType(APPLICATION_JSON)
                .content(input)
        )
            .andDo(print())
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.isApproved").value(false))
            .andExpect(jsonPath("$.requestedCreditLine").value(100.0))
    }
}