package com.ltp.viewandutils.utils

import java.util.regex.Pattern

/**
 * 检测是否合法的工具类，如手机号、邮箱等
 *
 * @author LTP 2021/1/13
 */
object ValidUtil {

    /**
     * 检测手机号是否合法
     *
     * @param phone 手机号
     * @return 手机号是否合法
     */
    fun isPhone(phone: String): Boolean {
        val phonePattern =
                Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(17[1-9]{1})|(18[0-9]{1}))+\\d{8})$")
        return phonePattern.matcher(phone).matches()
    }
}