package com.github.wenslo.kotlin.app.security

import com.github.wenslo.kotlin.app.entity.sys.User
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject

class ShiroUtils {
    companion object {
        fun getSubject(): Subject = SecurityUtils.getSubject()
        private fun getPrincipal(): Principal = getSubject().principal as Principal
        fun getUser(): User = getPrincipal().attributes.get("user") as User
    }
}