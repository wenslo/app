package com.github.wenslo.kotlin.app

/**
 * @author: 温海林
 * 2018年02月07日09:58:36
 */
fun main(args: Array<String>) {
//    println(Test().sum(1, 2))
//    Test().printSum(1, 2)
//    println(Test().maxOf(1, 2))
//    val items = listOf("apple", "banana", "kiwi")
//    for (index in items.indices) println("item at $index is ${items[index]}")
//    println(Test().describe("Hello"))
//    println(Test().describe(2))
//    println(Test().foo())
    print(getValue())
}


class Test {
    fun sum(a: Int, b: Int): Int = a + b
    fun printSum(a: Int, b: Int) {
        println("sum of $a and $b is ${a + b}")
    }

    fun maxOf(a: Int, b: Int) = if (a > b) a else b
    fun describe(obj: Any): String? = when (obj) {
        1 -> "One"
        "Hello" -> "Greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else -> "Unknown"
    }

    fun ifEcpression(a: Int, b: Int) {
        var max = a
        if (a > b) a else b
    }

    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
            if (value == 3) return  // 局部返回到匿名函数的调用者，即 forEach 循环
            print(value)
        })
        println(" done with anonymous function")
    }
}

fun add2(x: Int = 0, y: Int = 0): Int = x + y

fun operate(x: Int = 0, y: Int = 0, body: (Int, Int) -> Int) {
    println("this result is " + body(x, y))
}

fun getValue() {
    operate(3, 7, ::add2)//函数参数传入一个函数
    operate(3, 7, { x, y -> x + y })//函数参数传入一个lambda表达式
}

class Address {
    var setterVisibility: String = "abc"
        private set
        get() = this.toString()
}