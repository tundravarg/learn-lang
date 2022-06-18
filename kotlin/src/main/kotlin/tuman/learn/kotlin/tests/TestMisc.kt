package tuman.learn.kotlin

import tuman.learn.kotlin.utils.round
import tuman.learn.kotlin.utils.test


fun tests() {
    testRound()
}


private fun testRound() {
    test("Round") {
        val value = 9876543.3456789
        println("value: $value")
        println("value.4:  ${round(value, 4)}")
        println("value.3:  ${round(value, 3)}")
        println("value.2:  ${round(value, 2)}")
        println("value.1:  ${round(value, 1)}")
        println("value.0:  ${round(value, 0)}")
        println("value.-1: ${round(value, -1)}")
        println("value.-2: ${round(value, -2)}")
        println("value.-3: ${round(value, -3)}")
        println("value.-4: ${round(value, -4)}")
    }
}