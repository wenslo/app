package com.github.wenslo.kotlin.app.controller.sys

import com.github.wenslo.kotlin.app.condition.sys.UserCondition
import com.github.wenslo.kotlin.app.repository.sys.UserRepository
import com.github.wenslo.kotlin.app.repository.sys.UserSpecification
import com.github.wenslo.kotlin.app.service.sys.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var repository: UserRepository
    @Autowired
    lateinit var service: UserService

    @GetMapping("list")
    fun list(condition: UserCondition, pageable: Pageable, map: ModelMap): String {
        val page = repository.findAll(UserSpecification(condition), pageable)
        map.put("page", page)
        return "user"
    }

}