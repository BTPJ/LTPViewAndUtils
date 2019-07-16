package com.ltp.viewandutils.utils

import android.content.Context

/**
 * App版本相关工具类
 *
 * @author LTP  2019/7/11
 */
object VersionUtil {

    /**
     * 获取App版本号
     *
     * @return App版本号(-1表示获取失败)
     */
    fun getVersionCode(context: Context): Long {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    /**
     * 获取App版本名
     *
     * @return App版本名(""表示获取失败)
     */
    fun getVersionName(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}