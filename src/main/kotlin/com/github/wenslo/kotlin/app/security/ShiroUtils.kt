package com.github.wenslo.kotlin.app.security

import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject

class ShiroUtils {
    companion object {
        fun getSubject(): Subject = SecurityUtils.getSubject()
        fun getPrincipal(): Principal = getSubject().principal as Principal
    }
}