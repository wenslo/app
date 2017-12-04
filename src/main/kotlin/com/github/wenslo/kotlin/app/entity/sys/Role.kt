package com.github.wenslo.kotlin.app.entity.sys

import com.github.wenslo.kotlin.app.domain.StringListConverter
import com.github.wenslo.kotlin.app.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @author: 温海林
 * 2017年11月28日18:20:21
 */
@Entity
@Table(name = "role")
data class Role(
        val name: String = "",
        val title: String = "",
        @Convert(converter = StringListConverter::class)
        @Column(name = "permission", length = 2500)
        var permissionList: List<String> = emptyList()
) : BaseEntity()