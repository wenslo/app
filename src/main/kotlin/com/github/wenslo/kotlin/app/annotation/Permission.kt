package com.github.wenslo.kotlin.app.annotation

/**
 * @author: 温海林
 * 2017年12月22日10:34:57
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(val name: String = "")