package com.aliakseikaraliou.github.tribaldemo.extensions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)