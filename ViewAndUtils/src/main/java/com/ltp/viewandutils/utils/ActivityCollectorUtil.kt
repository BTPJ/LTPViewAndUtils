package com.ltp.viewandutils.utils

import android.app.Activity
import java.util.*

/**
 * Activity容器类，用于如退出程序等操作
 *
 * @author LTP  2017/11/2
 */
object ActivityCollectorUtil {

    /** 存储Activity的集合 */
    private val activityList = ArrayList<Activity>()

    /**
     * 集合中添加Activity,一般建立一个基类BaseActivity,然后在onCreate中调用，然后所有的Activity继承此基类BaseActivity
     *
     * @param activity Activity
     */
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**
     * 集合中移除Activity,一般建立一个基类BaseActivity,然后在onDestroyed中调用，然后所有的Activity继承此基类BaseActivity
     *
     * @param activity Activity
     */
    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * Finish掉所有的Activity
     */
    fun finishAll() {
        activityList
                .filterNot { it.isFinishing }
                .forEach { it.finish() }
    }
}
