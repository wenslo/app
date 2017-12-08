package com.github.wenslo.kotlin.app.repository.sys

import com.github.wenslo.kotlin.app.entity.sys.SystemLog
import org.springframework.data.repository.CrudRepository

/**
 * @author: 温海林
 * 2017年12月08日17:14:20
 */
interface SystemLogRepository : CrudRepository<SystemLog, Long> {

}