package com.github.wenslo.kotlin.app.security

import com.google.common.collect.Maps
import java.io.Serializable

data class Principal(
        var id: Long = 0,
        var name: String = "",
        var attributes: Map<String, Any> = Maps.newHashMap()
) : Serializable {
    constructor(id: Long, name: String) : this()

    companion object {
        val PERMISSION_ATTRIBUTE_KEY = "permissions"
        val ROLE_ATTRIBUTE_KEY = "roles"
    }
}