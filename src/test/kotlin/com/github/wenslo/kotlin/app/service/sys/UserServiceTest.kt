package com.github.wenslo.kotlin.app.service.sys

import com.github.wenslo.kotlin.app.AppApplicationTests
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 * @author: 温海林
 * 2017年11月23日14:21:50
 */
class UserServiceTest : AppApplicationTests() {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    @Autowired
    lateinit var userService: UserService

    @Test
    fun getTest() {
        val id = 1L;
        val user = userService.get(id)
        logger.debug("user is $user")
        assert(Objects.nonNull(user))
    }

    @Test
    fun findByPhoneAndPasswordTest() {
        val phone = "15234103550"
        val password = "123456"
        val user = userService.findByPhoneAndPassword(phone, password);
        logger.debug("user is $user")
        assert(Objects.nonNull(user))
    }
}