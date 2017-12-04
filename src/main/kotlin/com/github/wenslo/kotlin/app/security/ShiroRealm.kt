package com.github.wenslo.kotlin.app.security

import com.github.wenslo.kotlin.app.entity.sys.User
import com.github.wenslo.kotlin.app.service.sys.UserService
import com.google.common.collect.Lists
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


/**
 * 领域认证
 */
open class ShiroRealm : AuthorizingRealm() {

    private val logger: Logger = LoggerFactory.getLogger(this::getName.javaClass.name)

    @Autowired private lateinit var userService: UserService

    /**
     * 认证
     */
    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo? {
        val builder = StringBuilder()
        logger.info("method:{}，开始进行认证", this::getName)
        val userToken = token as UsernamePasswordToken
        val loginName = userToken.username
        userToken.password.forEach { builder.append(it) }
        val password = builder.toString()
        logger.info("login name is {}, password is {}", loginName, password)
        val user = userService.findByPhoneAndPassword(loginName, password)
        logger.info("user is {}", user)
        if (!Objects.equals(user?.password, password)) return null
        return SimpleAuthenticationInfo(getUserPrincipal(user ?: User()), password, name)
    }

    /**
     * 授权
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo? {
        if (principals?.fromRealm(name)?.iterator()?.hasNext() == null) return null
        val principal = principals.fromRealm(name).iterator().next() as Principal
        val info = SimpleAuthorizationInfo()
        val user = userService.get(principal.id)
        val permissionList = Lists.newArrayList<String>()
        val roleList = Lists.newArrayList<String>()
        for (role in user.roleList) {
            roleList.add(role.name)
            permissionList.addAll(role.permissionList)
        }
        info.addRoles(roleList)
        info.addStringPermissions(permissionList)
        principal.attributes = mapOf(Pair(Principal.PERMISSION_ATTRIBUTE_KEY, permissionList.distinct().toList()), Pair
        (Principal.ROLE_ATTRIBUTE_KEY, roleList))
        return SimpleAuthorizationInfo()
    }

    private fun getUserPrincipal(user: User): Principal {
        val prin = Principal(user.id, user.username)
        prin.attributes = mapOf(Pair("name", user.username))
        return prin
    }

}