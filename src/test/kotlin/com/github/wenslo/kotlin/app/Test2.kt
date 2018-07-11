package com.github.wenslo.kotlin.app

import com.github.wenslo.kotlin.app.Color.*
import java.io.BufferedReader
import java.io.StringReader

/**
 * @author: 温海林
 * 2018年02月08日16:32:51
 */
enum class Color(
        val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 265, 0),       // 2 当每个常量被创建时指定属性值
    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238); // 3 分号（;）在这里是必须的

    fun rgb() = (r * 256 + g) * 256 + b
}

fun getMnmonic(color: Color) =
        when (color) {
            RED -> "Richard"
            ORANGE -> "Of"
            YELLOW -> "York"
            GREEN -> "Grave"
            BLUE -> "Battle"
            INDIGO -> "In"
            VIOLET -> "Vain"
        }

fun getWarmth(color: Color) = when (color) {
    RED, ORANGE, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
}

fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) {
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty color")
        }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
        when (e) {
            is Num ->
                e.value
            is Sum ->
                eval(e.right) + eval(e.left)
            else ->
                throw IllegalArgumentException("")
        }

fun evalWithLogging(e: Expr): Int =
        when (e) {
            is Num -> {
                println("num : ${e.value}")
                e.value
            }
            is Sum -> {
                val left = evalWithLogging(e.left)
                val right = evalWithLogging(e.right)
                println("sum : $left + $right")
                left + right
            }
            else -> throw IllegalArgumentException("Unknown expression")
        }

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know ..."
}


fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException) {
        null
    }
    println(number)
}


fun main(args: Array<String>) {
    val reader = BufferedReader(StringReader("not a number"))
    readNumber(reader) // 2 什么也不会打印
}