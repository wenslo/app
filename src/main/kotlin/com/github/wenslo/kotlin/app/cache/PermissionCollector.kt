package com.github.wenslo.kotlin.app.cache

import com.github.wenslo.kotlin.app.annotation.Permission
import com.github.wenslo.kotlin.app.domain.PermissionDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

/**
 * 权限缓存
 * @author: 温海林
 * 2017年12月20日17:44:23
 */
@Component
class PermissionCollector : BeanPostProcessor {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private val permissions = mutableListOf<PermissionDTO>();


    override fun postProcessBeforeInitialization(bean: Any, beanName: String?): Any = bean

    override fun postProcessAfterInitialization(bean: Any, beanName: String?): Any {
//        logger.debug("beginning process bean , for permission , bean name is $beanName")
        if (beanName!!.indexOf("Permission") <= 1) {
            return bean
        }
        val kFunction1 = bean::class.java::getDeclaredFields
        val fields = kFunction1.invoke()
        fields.forEach { it ->
            run {
                if (it.get(bean).toString().indexOf("Companion") != -1) {

                } else {
                    var permission = PermissionDTO(name = it.getAnnotation(Permission::class.java).name, value = it.get(bean) as String)
                    permissions.add(permission)
                }

            }
        }
        return bean

    }

    fun getPermissionList() = permissions

}