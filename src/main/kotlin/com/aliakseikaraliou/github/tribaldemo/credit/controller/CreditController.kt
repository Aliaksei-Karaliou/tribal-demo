package com.aliakseikaraliou.github.tribaldemo.credit.controller

import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditApproveResult
import com.aliakseikaraliou.github.tribaldemo.credit.model.CreditLine
import com.aliakseikaraliou.github.tribaldemo.credit.service.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("credit")
class CreditController(private val creditService: CreditService) {

    @PostMapping
    fun applyForCredit(@RequestBody creditLine: CreditLine): ResponseEntity<CreditApproveResult> {
        val result = creditService.applyForCredit(creditLine)

        val status = if (result.isApproved) {
            HttpStatus.OK
        } else {
            HttpStatus.FORBIDDEN
        }

        return ResponseEntity(result, status)
    }
}