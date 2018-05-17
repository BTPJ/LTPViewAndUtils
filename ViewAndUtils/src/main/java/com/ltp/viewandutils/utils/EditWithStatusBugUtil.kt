package com.ltp.viewandutils.utils

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.widget.FrameLayout


/**
 * Android的一个Bug:当设置沉浸式通知栏后，弹出的软键盘会挡住底部的EditText，而不是推上去
 *
 * 调用EditWithStatusBugUtil.dealEditWithStatusBug(this)即可
 * 参考：https://www.jianshu.com/p/87795b9cda4b
 *
 * @author LTP  2018/5/17
 */
class EditWithStatusBugUtil(activity: Activity) {

    private var mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private var frameLayoutParams: FrameLayout.LayoutParams

    companion object {

        /**
         * 解决当设置沉浸式通知栏后，弹出的软键盘会挡住底部的EditText，而不是推上去的Bug
         *
         * @param activity Activity
         */
        fun dealEditWithStatusBug(activity: Activity) {
            EditWithStatusBugUtil(activity)
        }
    }

    init {
        val content = activity.findViewById(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent(activity) }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent(activity: Activity) {
        val usableHeightNow = computeUsableHeight(activity)
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(activity: Activity): Int {
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)

        //这个判断是为了解决19之后的版本在弹出软键盘时，键盘和推上去的布局（adjustResize）之间有黑色区域的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return (r.bottom - r.top) + statusBarHeight
        }
        return (r.bottom - r.top)
    }
}