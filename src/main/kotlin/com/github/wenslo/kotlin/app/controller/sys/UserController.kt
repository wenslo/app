package com.github.wenslo.kotlin.app.controller.sys

import com.github.wenslo.kotlin.app.annotation.OperationLog
import com.github.wenslo.kotlin.app.condition.sys.UserCondition
import com.github.wenslo.kotlin.app.entity.sys.User
import com.github.wenslo.kotlin.app.permisson.SysPermission
import com.github.wenslo.kotlin.app.repository.sys.RoleRepository
import com.github.wenslo.kotlin.app.repository.sys.UserRepository
import com.github.wenslo.kotlin.app.repository.sys.UserSpecification
import com.github.wenslo.kotlin.app.service.sys.UserService
import org.apache.shiro.authz.annotation.RequiresPermissions
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
    @Autowired
    lateinit var roleRepository: RoleRepository

    @ModelAttribute
    fun modelAttribute(map: ModelMap): ModelMap {
        map.put("title", "user manage")
        map.put("roles", roleRepository.findAll())
        map.put("view", null)
        return map
    }

    @RequestMapping("list", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @RequiresPermissions(SysPermission.UserPermission.view)
    fun list(condition: UserCondition, pageable: Pageable, map: ModelMap): String {
        logger.debug("method:list,condition:{},pageable:{}", condition, pageable)
        val page = repository.findAll(UserSpecification(condition), pageable)
        map.put("page", page)
        map.put("condition", condition)
        logger.debug("page is {}", page)
        return "user"
    }

    @OperationLog
    @RequestMapping("save", method = arrayOf(RequestMethod.POST))
    @RequiresPermissions(SysPermission.UserPermission.edit)
    fun save(user: User): String {
        logger.debug("method:save,parameter:{}", user)
        repository.save(user)
        return "redirect:/user/list"
    }

    @OperationLog
    @RequestMapping("delete/{id}", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @RequiresPermissions(SysPermission.UserPermission.remove)
    fun delete(@PathVariable id: Long): String {
        logger.debug("method:delete,id:{}", id).let { repository.delete(id).let { return "redirect:/user/list"; } }
    }

    @GetMapping("detail/{id}")
    @RequiresPermissions(SysPermission.UserPermission.view)
    fun detail(@PathVariable id: Long, flag: String?, map: ModelMap): String {
        logger.debug("method:detail,id:{},flag:{}", id, flag)
        map.put("update", flag)
        if (flag == null) map.put("view", "1") else map.put("view", null)
        map.put("user", repository.getOne(id))
        return "user_detail"
    }

    @OperationLog
    @RequestMapping("update", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @RequiresPermissions(SysPermission.UserPermission.edit)
    fun update(@RequestParam("id") id: Long, user: User, map: ModelMap): String {
        logger.debug("method:update,parameter :{}", user.toString())
        val oldUser = repository.getOne(id)
        val copy = oldUser.copy(username = user.username, phone = user.phone, password = user.password, status = user.status, roleList = user.roleList).let { repository.save(it) }
        logger.debug("to change user is {}", copy)
        map.put("message", "Update Success")
        return "redirect:/user/list"
    }

    @RequestMapping("addView")
    fun showAddView() = "user_detail"
}