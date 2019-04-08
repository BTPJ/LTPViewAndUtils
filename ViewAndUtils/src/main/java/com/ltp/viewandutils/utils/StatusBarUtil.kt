package com.ltp.viewandutils.utils

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

/**
 * 屏幕相关工具类
 *   1.设置沉浸式任务栏
 *
 * @author LTP 16/9/21.
 */
object StatusBarUtil {

    /**
     * 设置透明状态栏
     *
     * @param activity 要设置的Activity
     */
    fun setImmersionStatus(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            // 4.0-4.4由于Title的paddingTop的原因直接设置成全屏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * 设置Android6.0上状态栏的字体颜色为黑色
     *
     * @param activity Activity
     */
    fun setStatusBarLightMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    /**
     * 设置Android6.0上状态栏的字体颜色为黑色
     *
     * @param activity Activity
     */
    fun setStatusBarDarkMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }


    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return 包含虚拟键的整体屏幕高度
     */
    fun getScreenRealHeight(activity: Activity): Int {
        var dpi = 0
        val display = activity.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            dpi = dm.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dpi
    }
}
