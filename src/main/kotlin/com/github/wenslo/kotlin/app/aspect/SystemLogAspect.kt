package com.github.wenslo.kotlin.app.aspect

import com.github.wenslo.kotlin.app.entity.sys.SystemLog
import com.github.wenslo.kotlin.app.repository.sys.SystemLogRepository
import com.google.gson.GsonBuilder
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


/**
 * @author: 温海林
 * 2017年12月08日17:00:38
 */
@Aspect
@Component
class SystemLogAspect {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    lateinit var repository: SystemLogRepository
    val gson = GsonBuilder().create()

    //Controller层切点
    @Pointcut("@annotation(com.github.wenslo.kotlin.app.annotation.OperationLog)")
    fun controllerAspect() {

    }


    @Before("controllerAspect()")
    fun doBefore(joinPoint: JoinPoint) {
        val ra = RequestContextHolder.getRequestAttributes()
        val sra = ra as ServletRequestAttributes
        var request = sra.request
        val target = joinPoint.target
        logger.debug("joinPoint.signature：{}", joinPoint.signature)
        var log = SystemLog(operationParameter = joinPoint.args.joinToString { "；" },
                methodName = joinPoint.signature.toString())

    }
}