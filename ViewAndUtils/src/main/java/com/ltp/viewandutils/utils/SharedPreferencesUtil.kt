package com.ltp.viewandutils.utils

import android.content.Context
import android.widget.Toast

/**
 * SharedPreferences的一个工具类，调用writeData就能保存String, Integer, Boolean,
 * Float,Long类型的参数 同样调用readData就能获取到保存在手机里面的数据。
 *
 * @author LTP 2015年10月14日
 */
object SharedPreferencesUtil {
    /**
     * 保存在手机里面的文件名
     */
    private const val FILE_NAME = "share_data"

    @Suppress("CAST_NEVER_SUCCEEDS")
            /**
             * 写入数据并保存的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
             *
             * @param context 上下文
             * @param key     键
             * @param value   值
             */
    fun writeData(context: Context, key: String, value: String) {
        // 获取要存储数据的封装类名
        val type = value.javaClass.simpleName
        val sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when (type) {
            "String" -> editor.putString(key, value as String)
            "Integer" -> editor.putInt(key, value as Int)
            "Float" -> editor.putFloat(key, value as Float)
            "Boolean" -> editor.putBoolean(key, value as Boolean)
            "Long" -> editor.putLong(key, value as Long)
            else -> Toast.makeText(context, "不支持存储此类型的对象", Toast.LENGTH_SHORT).show()
        }
        editor.apply()
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 当无法查到指定的键时岁返回的指定默认值
     * @return 存储的对象
     */
    fun readData(context: Context, key: String, defValue: Any): Any? {
        val type = defValue.javaClass.simpleName
        val sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        return when (type) {
            "String" -> sharedPreferences.getString(key, defValue as String)
            "Integer" -> sharedPreferences.getInt(key, defValue as Int)
            "Float" -> sharedPreferences.getFloat(key, defValue as Float)
            "Boolean" -> sharedPreferences.getBoolean(key, defValue as Boolean)
            "Long" -> sharedPreferences.getLong(key, defValue as Long)
            else -> null
        }
    }
}
