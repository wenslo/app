package com.github.wenslo.kotlin.app.entity.sys

import com.github.wenslo.kotlin.app.entity.BaseEntity
import com.github.wenslo.kotlin.app.entity.sys.enum.DisableStatus
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) override var id: Long = 0,
        @CreatedDate override var createdAt: Date = Date(),
        @LastModifiedDate override var updatedAt: Date = Date(),
        @Column(name = "username")
        var username: String = "",
        //手机
        var phone: String = "",
        //密码
        var password: String = "",
        //禁用状态
        @Enumerated(EnumType.STRING)
        var status: DisableStatus = DisableStatus.DISABLE,
        //头像
        @Column(name = "head_portrait")
        var headPortrait: String = "",
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_role", joinColumns = arrayOf(JoinColumn(name = "user_id")), inverseJoinColumns
        = arrayOf(JoinColumn(name = "role_id")))
        var roleList: List<Role> = emptyList()
) : BaseEntity()