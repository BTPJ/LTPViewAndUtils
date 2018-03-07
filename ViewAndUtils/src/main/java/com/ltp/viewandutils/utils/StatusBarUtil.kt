package com.ltp.viewandutils.utils

import android.app.Activity
import android.os.Build
import android.view.WindowManager

/**
 * 屏幕相关工具类
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
            // 4.0-4.4由于Title的padingTop的原因直接设置成全屏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}
