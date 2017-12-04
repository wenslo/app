package com.github.wenslo.kotlin.app

fun main(args: Array<String>) {
    val password = arrayOf<Char>('1', '2', '3', '4', '5', '6')
    println(password.toString() is String)
    println(password.toString())
    println(password.contentToString() is String)
    println(password.contentToString())
}