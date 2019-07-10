package com.ltp.ltpviewandutils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.ltp.viewandutils.utils.*
import com.ltp.viewandutils.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * LoadingDialog加载框的测试界面
 *
 * @author LTP 2018/3/2
 */
class MainActivity : Activity() {

    private var i = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        StatusBarUtil.setImmersionStatus(this)
        StatusBarUtil.setStatusBarLightMode(this)
        tv_test.setOnClickListener {
            //            LoadingDialog.Builder(this).create().show()
//            ToastUtil.showShort(this, "测试${i++}")
            LoadingDialog.with(this).setMessage("hah").show()
        }
        LogUtil.d("缓存大小" + FileUtil.getTotalCacheSize(this))
        for (i in 1..20) {
            val textView = TextView(this)
            textView.text = "测试$i"
            flow.addView(textView)
        }

        tv_test.text = "屏幕高度：${DensityUtil.getScreenHeight(this)}\n屏幕宽度：${DensityUtil.getScreenWidth(this)}\n" +
                "屏幕密度：${DensityUtil.getScreenDensity(this)}"
    }
}
