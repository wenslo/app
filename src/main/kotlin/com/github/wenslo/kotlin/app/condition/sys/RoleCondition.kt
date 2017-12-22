package com.github.wenslo.kotlin.app.condition.sys

import java.io.Serializable

/**
 * 角色查询条件
 * @author: 温海林
 * 2017年12月11日14:14:51
 */
data class RoleCondition(
        var name: String = "",
        var title: String = ""
) : Serializable