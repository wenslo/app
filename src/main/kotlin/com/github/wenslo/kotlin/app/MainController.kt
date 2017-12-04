package com.github.wenslo.kotlin.app

import com.github.wenslo.kotlin.app.domain.Response
import com.github.wenslo.kotlin.app.security.ShiroUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


/**
 * 登录控制器
 * @author: 温海林
 * 2017年11月23日10:12:07
 */
@Controller
@RequestMapping
class MainController {
    private val logger: Logger = LoggerFactory.getLogger(MainController::class.java)
    /**
     * 用户登录
     */
    @RequestMapping(value = "login")
    fun login(@RequestParam("username") username: String,
              @RequestParam("password") password: String,
              @RequestParam(defaultValue = "false", required = false) rememberMe: Boolean,
              map: ModelMap
    ): String {
        var subject = ShiroUtils.getSubject()
        var token = UsernamePasswordToken(username, password, rememberMe)
        try {
            subject.login(token)
        } catch (ex: AuthenticationException) {
            logger.error("username or password error")
            map.addAttribute("errorMessage", "username or password error")
            return "login"
        }
        return "index"
    }

    /**
     * 用户登出
     */
    @ResponseBody
    @RequestMapping(value = "logout")
    fun logout(): Response<Void> {
        var subject = ShiroUtils.getSubject()
        subject.logout()
        return Response.SUCCESS
    }

    @RequestMapping(value = "/main")
    fun index(map: ModelMap): String {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com")
        return "login"
    }

}