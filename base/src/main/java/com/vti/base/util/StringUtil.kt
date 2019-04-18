package com.vti.base.util

import java.text.Normalizer
import java.util.regex.Pattern

object StringUtil {
    fun removeAccent(s: String): String {
        val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(temp).replaceAll("Đ|đ")
    }
}