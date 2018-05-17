package com.ltp.viewandutils.utils

import android.app.Activity
import android.graphics.Rect
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
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
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

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

}