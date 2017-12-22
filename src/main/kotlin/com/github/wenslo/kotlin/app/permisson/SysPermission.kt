package com.github.wenslo.kotlin.app.permisson

import com.github.wenslo.kotlin.app.annotation.Permission
import org.springframework.stereotype.Component

/**
 * @author: 温海林
 * 2017年12月18日09:30:00
 */
@Component
class SysPermission {
    companion object {
        @Permission("System-All") const val all = "sys:*"
        @Permission("System-Add") const val add = "sys:*:add"
        @Permission("System-View") const val view = "sys:*:view"
        @Permission("System-Edit") const val edit = "sys:*:edit"
        @Permission("System:Remove") const val remove = "sys:*:remove"
    }

    @Component
    class RolePermission {
        companion object {
            @Permission("System-Role-All") const val all = "sys:role:*"
            @Permission("System-Role-Add") const val add = "sys:role:add"
            @Permission("System-Role-View") const val view = "sys:role:view"
            @Permission("System-Role-Edit") const val edit = "sys:role:edit"
            @Permission("System-Role-Remove") const val remove = "sys:role:remove"
        }
    }

    @Component
    class UserPermission {
        companion object {
            @Permission("System-User-All") const val all = "sys:user:*"
            @Permission("System-User-Add") const val add = "sys:user:add"
            @Permission("System-User-View") const val view = "sys:user:view"
            @Permission("System-User-Edit") const val edit = "sys:user:edit"
            @Permission("System-User-Remove") const val remove = "sys:user:remove"
        }
    }
}

//fun main(args: Array<String>) {
//    var p = SysPermission
//    val kFunction0 = SysPermission::class.java::getDeclaredFields
//    val fields = kFunction0.invoke()
//    fields.forEach { it ->
//        run {
//            if (it.get(SysPermission).toString().indexOf("Companion") != -1) {
//                return
//            }
//            println(it.get(SysPermission))
//        }
//    }
//}