package com.github.wenslo.kotlin.app.controller.sys

import com.github.wenslo.kotlin.app.annotation.OperationLog
import com.github.wenslo.kotlin.app.cache.PermissionCollector
import com.github.wenslo.kotlin.app.condition.sys.RoleCondition
import com.github.wenslo.kotlin.app.entity.sys.Role
import com.github.wenslo.kotlin.app.permisson.SysPermission
import com.github.wenslo.kotlin.app.repository.sys.RoleRepository
import com.github.wenslo.kotlin.app.repository.sys.RoleSpecification
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*

/**
 * 角色控制器
 * @author: 温海林
 * 2017年12月11日14:04:48
 */
@Controller
@RequestMapping("/role")
class RoleController {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    @Autowired
    lateinit var repository: RoleRepository
    @Autowired
    lateinit var collector: PermissionCollector;

    @ModelAttribute
    fun modelAttribute(map: ModelMap): ModelMap {
        map.put("title", "Role Manage")
        map.put("view", null)
        return map
    }

    @RequiresPermissions(SysPermission.RolePermission.view)
    @RequestMapping("list", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun list(condition: RoleCondition, pageable: Pageable, map: ModelMap): String {
        logger.debug("method:list,condition:{},pageable:{}", condition, pageable)
        val page = repository.findAll(RoleSpecification(condition), pageable)
        map.put("page", page)
        map.put("condition", condition)
        logger.debug("page is {}", page)
        return "role"
    }

    @OperationLog
    @RequestMapping("save")
    @RequiresPermissions(SysPermission.RolePermission.edit)
    fun save(name: String, title: String, permissionList: String): String {
        val role = Role(name = name, title = title, permissionList = listOf(permissionList))
        logger.debug("method:save, parameter :$role")
        repository.save(role)
        return "redirect:/role/list"
    }

    @OperationLog
    @RequestMapping("delete/{id}", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @RequiresPermissions(SysPermission.RolePermission.remove)
    fun delete(@PathVariable id: Long): String {
        logger.debug("method:delete,id:{}", id)
        repository.delete(id)
        return "redirect:/role/list"
    }

    @GetMapping("detail/{id}")
    @RequiresPermissions(SysPermission.RolePermission.view)
    fun detail(@PathVariable id: Long, flag: String?, map: ModelMap): String {
        logger.debug("method:detail,id:$id,flag:$flag")
        map.put("update", flag)
        if (flag == null) map.put("view", "1") else map.put("view", null)
        map.put("role", repository.getOne(id))
        map.put("permissions", collector.getPermissionList())
        return "role_detail"
    }


    @OperationLog
    @RequestMapping("update", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    @RequiresPermissions(SysPermission.RolePermission.edit)
    fun update(@RequestParam("id") id: Long, name: String, title: String, permissionList: String, map: ModelMap): String {
        logger.debug("method:update,id:$id,name:$name,title:$title")
        val oldRole = repository.getOne(id)
        val copy = oldRole.copy(name = name, title = title, permissionList = listOf(permissionList))
        repository.save(copy)
        logger.debug("to change role is {}", copy)
        map.put("message", "Update Success")
        return "redirect:/role/list"
    }

    @RequestMapping("listNoPage", method = arrayOf(RequestMethod.GET, RequestMethod.POST))
    @ResponseBody
    @RequiresPermissions(SysPermission.RolePermission.view)
    fun listNoPage(): List<Role> = logger.debug("method:listNoPage").let { repository.findAll() }

    @RequestMapping("addView")
    fun showAddView(map: ModelMap): String {
        map.put("permissions", collector.getPermissionList())
        return "role_detail"
    }

}