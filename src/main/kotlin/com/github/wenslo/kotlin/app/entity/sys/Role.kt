package com.github.wenslo.kotlin.app.entity.sys

import com.github.wenslo.kotlin.app.domain.StringListConverter
import com.github.wenslo.kotlin.app.entity.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

/**
 * @author: 温海林
 * 2017年11月28日18:20:21
 */
@Entity
@Table(name = "role")
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) override var id: Long = 0,
        @CreatedDate override var createdAt: Date = Date(),
        @LastModifiedDate override var updatedAt: Date = Date(),
        val name: String = "",
        val title: String = "",
        @Convert(converter = StringListConverter::class)
        @Column(name = "permission", length = 2500)
        var permissionList: List<String> = emptyList()
) : BaseEntity()