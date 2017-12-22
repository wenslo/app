package com.github.wenslo.kotlin.app.service.sys.impl

import com.github.wenslo.kotlin.app.entity.sys.User
import com.github.wenslo.kotlin.app.repository.sys.UserRepository
import com.github.wenslo.kotlin.app.service.sys.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun findByPhoneAndPassword(phone: String, password: String): User? = userRepository
            .findByPhoneAndPassword(phone, password);

    override fun get(id: Long): User = userRepository.getOne(id)


}