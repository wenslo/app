package com.github.wenslo.kotlin.app

data class A(
        var name: String = "123",
        var age: Int = 12
)


fun main(args: Array<String>) {
    var a = A();
    var b = A("222", 1);
    val copy = b.copy(name = "999")
    println(a)
    println(b)
    println(copy)
}