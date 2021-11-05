package com.aliakseikaraliou.github.tribaldemo.credit.model

import java.io.Serializable

data class Business(
    val type: BusinessType,
    val moneyFlow: BusinessMoneyFlow
) : Serializable