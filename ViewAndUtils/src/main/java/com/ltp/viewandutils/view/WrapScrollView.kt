package com.ltp.viewandutils.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import com.ltp.viewandutils.R
import com.ltp.viewandutils.utils.ScreenUtil

/**
 * 可设置最大高度占据屏幕比例的ScrollView(超过可滑动),最小高度为自适应子控件的高度
 *
 * @author LTP  2018/9/13
 */
class WrapScrollView(context: Context?, attrs: AttributeSet?) : ScrollView(context, attrs) {

    /** 最大高度占屏幕的比例(默认是0.5) */
    private var maxHeightRatio = 0.5f

    init {
        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.WrapScrollView)
        maxHeightRatio = typeArray?.getFloat(R.styleable.WrapScrollView_maxHeightRatio, 0.5f) ?: 0.5f
        typeArray?.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var newHeightMeasureSpec = heightMeasureSpec
        try {
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec((ScreenUtil.getScreenHeight(context) * maxHeightRatio).toInt(), MeasureSpec.AT_MOST)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    /**
     * 设置最大高度占据屏幕的比例(默认是0.5)
     *
     * @param maxHeightRatio 最大高度占据屏幕的比例
     */
    fun setMaxHeightRatio(maxHeightRatio: Float) {
        this.maxHeightRatio = maxHeightRatio
    }
}