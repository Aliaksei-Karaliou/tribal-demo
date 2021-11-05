package com.aliakseikaraliou.github.tribaldemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class TribalDemoApplication

fun main(args: Array<String>) {
    runApplication<TribalDemoApplication>(*args)
}
