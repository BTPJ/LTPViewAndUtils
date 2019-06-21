package com.ltp.viewandutils.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.ltp.viewandutils.R
import kotlinx.android.synthetic.main.dialog_loading.view.*

/**
 * 自定义的仿IOS的加载框
 *
 * @author LTP  2018/3/2
 */
class LoadingDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    /**
     * 使用Builder模式构建
     */
    class Builder(private val context: Context) {

        /** 加载框下方的提示信息(默认为加载中...) */
        private var message = "加载中..."
        /** 是否显示加载框下方的提示信息(默认显示) */
        private var isShowMessage = true
        /** 是否点击返回键取消显示加载框(默认不取消) */
        private var isCancelable = true
        /** 是否点击加载框以外取消显示加载框(默认不取消) */
        private var isCancelOutside = false

        /**
         * 设置加载框下方的提示信息
         *
         * @param message 提示信息
         * @return Builder对象
         */
        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        /**
         * 设置是否显示提示信息
         *
         * @param isShowMessage 是否显示提示信息
         * @return Builder对象
         */
        fun setShowMessage(isShowMessage: Boolean): Builder {
            this.isShowMessage = isShowMessage
            return this
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable 是否可以按返回键取消
         * @return Builder对象
         */
        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * 设置是否可以点击外部取消
         *
         * @param isCancelOutside 是否可以点击外部取消
         * @return Builder对象
         */
        fun setCancelOutside(isCancelOutside: Boolean): Builder {
            this.isCancelOutside = isCancelOutside
            return this
        }

        /**
         * 创建LoadingDialog加载框对象
         *
         * @return LoadingDialog加载框对象
         */
        @SuppressLint("InflateParams")
        fun create(): LoadingDialog {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
            val loadingDialog = LoadingDialog(context, R.style.LoadingDialogStyle)
            if (isShowMessage) {
                view.tv_loading.text = message
            } else {
                view.tv_loading.visibility = View.GONE
            }
            loadingDialog.setContentView(view)
            loadingDialog.setCancelable(isCancelable)
            loadingDialog.setCanceledOnTouchOutside(isCancelOutside)
            return loadingDialog
        }
    }
}

