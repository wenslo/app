package com.github.wenslo.kotlin.app.extension

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Predicate

/**
 * CriteriaBuilder查询方法扩展
 * @author: 温海林
 * 2017年12月04日17:41:17
 */


/**
 * startLike
 */
fun CriteriaBuilder.startWith(exp: Expression<String>, value: String): Predicate = this.like(exp, value + "%")

/**
 * endLike
 */
fun CriteriaBuilder.endWith(exp: Expression<String>, value: String): Predicate = this.like(exp, "%" + value)