package com.github.wenslo.kotlin.app.extension

/**
 * 基础方法扩展
 * @author: 温海林
 * 2017年12月04日17:44:48
 */

inline fun <R> Boolean.then(block: () -> R?) = if (this) block() else null