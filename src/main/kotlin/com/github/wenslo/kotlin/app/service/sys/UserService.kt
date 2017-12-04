package com.github.wenslo.kotlin.app.service.sys

import com.github.wenslo.kotlin.app.entity.sys.User
import org.springframework.data.domain.Page

interface UserService {
    /**
     * 简单分页
     */
    fun findPage(pageNumber: Int, pageSize: Int): Page<User>

    /**
     * 根据手机和密码查询用户
     */
    fun findByPhoneAndPassword(phone: String, password: String): User?

    /**
     * 根据ID查询用户
     */
    fun get(id: Long): User
}