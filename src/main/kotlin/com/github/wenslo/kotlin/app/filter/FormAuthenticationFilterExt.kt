package com.github.wenslo.kotlin.app.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.wenslo.kotlin.app.domain.Response
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter
import java.io.ByteArrayOutputStream
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * 用户认证拦截器
 * @author: 温海林
 * 2017年11月23日09:49:33
 */
class FormAuthenticationFilterExt : FormAuthenticationFilter() {
    var content = byteArrayOf()

    init {
        var out = ByteArrayOutputStream()
        var om = ObjectMapper()
        om.writer().writeValue(out, Response.UN_AUTHENTICATION)
        content = out.toByteArray()
        out.close()
    }

    override fun onAccessDenied(request: ServletRequest?, response: ServletResponse?): Boolean {
        response?.outputStream?.write(this.content)
        return false
    }
}