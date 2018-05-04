package com.ltp.ltpviewandutils

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.ltp.viewandutils.utils.ToastUtil
import com.ltp.viewandutils.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * LoadingDialog加载框的测试界面
 *
 * @author LTP 2018/3/2
 */
class MainActivity : Activity() {

    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_test.setOnClickListener {
            LoadingDialog.Builder(this).create().show()
            ToastUtil.showShort(this, "测试${i++}")
        }
        for (i in 1..20) {
            val textView = TextView(this)
            textView.text = "测试$i"
            flow.addView(textView)
        }
    }
}
