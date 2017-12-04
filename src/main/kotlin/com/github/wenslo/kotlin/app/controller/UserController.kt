package com.github.wenslo.kotlin.app.controller

import com.github.wenslo.kotlin.app.domain.Response
import com.github.wenslo.kotlin.app.repository.UserReposiory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var repository: UserReposiory

    @GetMapping("list")
    fun list(name: String?, map: ModelMap): String {
        val users = repository.findAll()
        map.put("users", users)
        return "user"
    }

    @RequestMapping("remove", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun remove(): Response<*> = Response.SUCCESS

}