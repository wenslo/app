package com.github.wenslo.kotlin.app.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.io.Serializable


/**
 * @author: 温海林
 * 2017年11月23日09:56:35
 */
@JsonInclude(Include.NON_NULL)
class Response<T> @JvmOverloads constructor(var code: Int, var msg: String?, data: T? = null) : Serializable {
    var data: Any? = null

    init {
        this.data = data
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Response [code=").append(code).append(", msg=").append(msg).append(", data=").append(data)
                .append("]")
        return builder.toString()
    }

    companion object {
        //codes
        public val SUCCESS_CODE = 0
        public val ERROR_CODE = -1
        public val UN_AUTHENTICATION_CODE = 401
        public val UN_AUTHORIZATION_CODE = 403
        public val INTERNAL_EXCEPTION_CODE = 503
        //msgs
        public val SUCCESS_MSG = "success"
        public val ERROR_MSG = "error"
        public val UN_AUTHENTICATION_MSG = "unAuthentication"
        public val UN_AUTHORIZATION_MSG = "unAuthorization"
        public val INTERNAL_EXCEPTION_MSG = "internalException"

        //操作成功
        public val SUCCESS: Response<Void> = Response(SUCCESS_CODE, SUCCESS_MSG)
        //操作失败
        public val ERROR: Response<*> = Response<Any>(ERROR_CODE, ERROR_MSG)
        //用户未认证
        val UN_AUTHENTICATION: Response<Void> = Response(UN_AUTHENTICATION_CODE,
                UN_AUTHENTICATION_MSG)
        //用户未授权
        public val UN_AUTHORIZATION: Response<Void> = Response(UN_AUTHORIZATION_CODE, UN_AUTHORIZATION_MSG)
        //系统内部错误
        public val INTERNAL_EXCEPTION: Response<Void> = Response(INTERNAL_EXCEPTION_CODE,
                INTERNAL_EXCEPTION_MSG)

        /**
         * 构建一个通用成功的响应
         */
        fun <T> success(data: T): Response<T> = Response(SUCCESS_CODE, SUCCESS_MSG, data)

        /**
         * 构建一个通用失败响应
         */
        fun <T> error(msg: String): Response<T> = Response(ERROR_CODE, msg)

        /**
         * 构建一个失败响应
         */
        fun <T> error(code: Int, msg: String): Response<T> = Response(code, msg)
    }

}