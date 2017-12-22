package com.github.wenslo.kotlin.app.aspect

import com.github.wenslo.kotlin.app.annotation.OperationLog
import com.github.wenslo.kotlin.app.entity.sys.SystemLog
import com.github.wenslo.kotlin.app.entity.sys.enum.LogType
import com.github.wenslo.kotlin.app.extension.append
import com.github.wenslo.kotlin.app.repository.sys.SystemLogRepository
import com.github.wenslo.kotlin.app.security.ShiroUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


/**
 * 日志记录
 * @author: 温海林
 * 2017年12月08日17:00:38
 */
@Aspect
@Component
class SystemLogAspect {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired lateinit var repository: SystemLogRepository

    //Controller层切点
    @Pointcut("@annotation(com.github.wenslo.kotlin.app.annotation.OperationLog)")
    fun controllerAspect() {

    }


    @Before("controllerAspect() && @annotation(operationLog)")
    fun doBefore(joinPoint: JoinPoint, operationLog: OperationLog) {
        val user = ShiroUtils.getUser()
        logger.debug("description：{}", operationLog.description)
        val log = SystemLog(operationParameter = Arrays.toString(joinPoint.args),
                methodName = joinPoint.signature.toString(),
                operationUser = "id".append(":").append(user.id).append(",").append("name").append(":").append(user
                        .username),
                type = LogType.OPERATION,
                methodDescription = operationLog.description)
        repository.save(log)
    }
}