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
import org.springframework.stereotype.Component
import java.util.*


/**
 * @author: 温海林
 * 2017年12月08日17:00:38
 */
@Aspect
@Component
class SystemLogAspect {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    lateinit var repository: SystemLogRepository

    //Controller层切点
    @Pointcut("@annotation(com.github.wenslo.kotlin.app.annotation.OperationLog)")
    fun controllerAspect() {

    }


    @Before("controllerAspect()")
    fun doBefore(joinPoint: JoinPoint) {
        logger.debug("joinPoint.signature：{}", joinPoint.signature)
        logger.debug("joinPoint.args：{}", Arrays.toString(joinPoint.args))
        val user = ShiroUtils.getUser()
        logger.debug("user：{},{}", user.id, user.username)
        val operationLog = joinPoint.signature.javaClass.getAnnotation(OperationLog::class.java)
        val log = SystemLog(operationParameter = Arrays.toString(joinPoint.args),
                methodName = joinPoint.signature.toString(),
                operationUser = user.id.append("：").append(user.username),
                type = LogType.OPERATION,
                methodDescription = operationLog.description)
        repository.save(log)

    }
}