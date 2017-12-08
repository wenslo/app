package com.github.wenslo.kotlin.app.entity.sys

import com.github.wenslo.kotlin.app.entity.BaseEntity
import com.github.wenslo.kotlin.app.entity.sys.enum.LogType
import javax.persistence.*

/**
 * 操作日志记录类
 * @author: 温海林
 * 2017年12月08日15:27:53
 */
@Entity
@Table(name = "system_log")
data class SystemLog(
        @Column(name = "method_name")
        val methodName: String = "",
        @Column(name = "method_description")
        val methodDescription: String = "",
        @Column(name = "operation_user")
        val operationUser: String = "",
        @Column(name = "operation_parameter")
        val operationParameter: String = "",
        @Enumerated(EnumType.STRING)
        val type: LogType = LogType.OPERATION
) : BaseEntity()