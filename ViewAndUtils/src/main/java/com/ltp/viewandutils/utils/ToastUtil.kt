package com.ltp.viewandutils.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * Toast封装工具类
 *
 * @author LTP  2018/3/26
 */
object ToastUtil {

    /** 全局唯一的Toast */
    private var mToast: Toast? = null

    /**
     * 显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShort(context: Context, msg: String) {
        initShortToast(context, msg)
        mToast!!.show()
    }

    /**
     * 显示长时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLong(context: Context, msg: String) {
        initLongToast(context, msg)
        mToast!!.show()
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShortInCenter(context: Context, msg: String) {
        initShortToast(context, msg)
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        mToast!!.show()
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLongInCenter(context: Context, msg: String) {
        initLongToast(context, msg)
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        mToast!!.show()
    }

    /**
     * 初始化短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    @SuppressLint("ShowToast")
    private fun initShortToast(context: Context, msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(msg)
        }
        mToast!!.duration = Toast.LENGTH_SHORT
    }

    /**
     * 初始化Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    @SuppressLint("ShowToast")
    private fun initLongToast(context: Context, msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        } else {
            mToast!!.setText(msg)
        }
        mToast!!.duration = Toast.LENGTH_LONG
    }
}