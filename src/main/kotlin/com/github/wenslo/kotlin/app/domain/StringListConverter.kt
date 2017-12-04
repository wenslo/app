package com.github.wenslo.kotlin.app.domain

import javax.persistence.AttributeConverter
import javax.persistence.Converter


/**
 * @author: 温海林
 * 2017年11月28日18:27:43
 */
@Converter
class StringListConverter : AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(list: List<String>?): String? = list?.joinToString(",")

    override fun convertToEntityAttribute(joined: String?): List<String> {
        return if (joined == null || joined.isEmpty()) {
            emptyList()
        } else joined.split(",").toList()
    }

}