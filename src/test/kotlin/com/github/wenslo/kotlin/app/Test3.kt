package com.github.wenslo.kotlin.app

/**
 * @author: 温海林
 * 2018年02月28日17:31:49
 */
fun String.lastChar(): Char = get(length - 1)

fun <T> Collection<T>.joinToString(
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(spliterator())
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}