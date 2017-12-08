package com.github.wenslo.kotlin.app.controller.sys

import com.github.wenslo.kotlin.app.condition.sys.UserCondition
import com.github.wenslo.kotlin.app.entity.sys.User
import com.github.wenslo.kotlin.app.repository.sys.UserRepository
import com.github.wenslo.kotlin.app.repository.sys.UserSpecification
import com.github.wenslo.kotlin.app.service.sys.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/user")
class UserController {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    @Autowired
    lateinit var repository: UserRepository
    @Autowired
    lateinit var service: UserService

    @ModelAttribute
    fun modelAttribute(map: ModelMap): ModelMap {
        map.put("title", "user manage")
        map.put("update", "user manage")
        return map
    }

    @RequestMapping("list", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun list(condition: UserCondition, pageable: Pageable, map: ModelMap): String {
        logger.debug("method:list,condition:{},pageable:{}", condition, pageable)
        val page = repository.findAll(UserSpecification(condition), pageable)
        map.put("page", page)
        map.put("condition", condition)
        logger.debug("page is {}", page)
        return "user"
    }


    @RequestMapping("save", method = arrayOf(RequestMethod.POST))
    fun save(user: User) {
        logger.debug("method:save,parameter:{}", user)
        repository.save(user).let { "redirect:/user/list"; }
    }

    @RequestMapping("delete/{id}", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun delete(@PathVariable id: Long) {
        logger.debug("method:delete,id:{}", id)
        repository.delete(id).let { "redirect:/user/list"; }
    }

    @GetMapping("detail/{id}")
    @ResponseBody
    fun detail(@PathVariable id: Long): User =
            logger.debug("method:detail,id:{}", id).let { repository.getOne(id) }

    @RequestMapping("update", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun update(@RequestParam("id") id: Long, user: User, map: ModelMap): String {
        logger.debug("method:update,parameter :{}", user.toString())
        val oldUser = repository.getOne(id)
        val copy = oldUser.copy(username = user.username, phone = user.phone, password = user.password, status = user.status).let { repository.save(it) }
        logger.debug("to change user is {}", copy)
        map.put("message", "Update Success")
        return "redirect:/user/list"
    }
}