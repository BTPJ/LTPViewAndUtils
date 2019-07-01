package com.ltp.viewandutils.view

import android.app.Dialog
import android.content.Context
import android.view.View
import com.ltp.viewandutils.R
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * 自定义的仿IOS的加载框
 *
 * @author LTP  2018/3/2
 */
class LoadingDialog private constructor(context: Context) : Dialog(context, R.style.LoadingDialogStyle) {

    companion object {
        private var mLoadingDialog: LoadingDialog? = null

        /**
         * 创建LoadingDialog加载框对象
         *
         * @return LoadingDialog加载框对象
         */
        fun with(context: Context): LoadingDialog {
            if (mLoadingDialog == null) {
                mLoadingDialog = LoadingDialog(context)
            }
            return mLoadingDialog!!
        }
    }

    init {
        setContentView(R.layout.dialog_loading)
        // 是否点击返回键取消显示加载框(默认取消)
        setCancelable(true)
        // 是否点击加载框以外取消显示加载框(默认不取消)
        setCanceledOnTouchOutside(false)
    }


    /**
     * 设置加载框下方的提示信息
     *
     * @param message 提示信息
     * @return LoadingDialog对象
     */
    fun setMessage(message: String): LoadingDialog {
        tv_loading.text = message
        return mLoadingDialog!!
    }

    /**
     * 设置是否显示提示信息
     *
     * @param isShowMessage 是否显示提示信息
     * @return LoadingDialog对象
     */
    fun setShowMessage(isShowMessage: Boolean): LoadingDialog {
        tv_loading.visibility = if (isShowMessage) View.VISIBLE else View.GONE
        return mLoadingDialog!!
    }

    /**
     * 设置是否可以按返回键取消
     *
     * @param isCancelable 是否可以按返回键取消
     * @return LoadingDialog对象
     */
    fun setCanceled(isCancelable: Boolean): LoadingDialog {
        setCanceled(isCancelable)
        return mLoadingDialog!!
    }

    /**
     * 设置是否可以点击外部取消
     *
     * @param isCancelOutside 是否可以点击外部取消
     * @return LoadingDialog对象
     */
    fun setCancelOutside(isCancelOutside: Boolean): LoadingDialog {
        setCancelOutside(isCancelOutside)
        return mLoadingDialog!!
    }
}

