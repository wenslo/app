package com.autobots.led.entity.account


/**
 * 权限集合
 * @author NewYoung
 * 2016年2月23日下午6:10:16
 */
public enum class Permissions(var permissionName: String,
                              var permission: String,
                              var group: String
) {
    ALL("所有权限", "*", "admin")//超级管理员
    /**用户权限**/
    ,
    USER_ALL("用户所有权限", "user:*", "user"), USER_VIEW("查看用户", "user:view", "user"), USER_EDIT("用户修改",
            "user:edit", "user"),
    USER_REMOVE("用户删除", "user:remove", "user")//
    /**角色权限**/
    ,
    ROLE_ALL("角色所有权限", "role:*", "role"), ROLE_VIEW("角色查看", "role:view", "role"), ROLE_EDIT("角色修改",
            "role:edit", "role"),
    ROLE_REMOVE("角色删除", "user:remove", "role");//;

}