package com.github.wenslo.kotlin.app.annotation

/**
 * 操作日志注解
 * @author: 温海林
 * 2017年12月08日15:18:43
 */
@Target(AnnotationTarget.FUNCTION)
@Retention // default true
@MustBeDocumented
annotation class OperationLog