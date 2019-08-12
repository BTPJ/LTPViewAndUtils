package com.shuidun.dimencreate

import java.io.File

/**
 * dimens文件生成主类 https://github.com/ladingwu/dimens_sw
 *
 * @author LTP  2018/10/29
 */
object DimenCreate {

    /** 设计稿尺寸(将自己设计师的设计稿的宽度px填入) */
    private var DESIGN_WIDTH = 360

    /** 设计稿的高度  （将自己设计师的设计稿的高度px填入） */
    private var DESIGN_HEIGHT = 703

    @JvmStatic
    fun main(args: Array<String>) {
        val smallest = if (DESIGN_WIDTH > DESIGN_HEIGHT) DESIGN_HEIGHT else DESIGN_WIDTH  //     求得最小宽度
        // 要生成的dimens文件的最小宽度（Smallest-width）限定符
        val values = intArrayOf(300, 310, 320, 330, 330, 340, 350, 360, 370, 384, 390, 400, 411, 420, 432, 440, 450, 480, 533, 600)
        val directory = File("MyValuesDp")
        for (value in values) {
            //为了方便，设定为当前文件夹，dimens文件将会生成项目所在文件夹中，用户可自行更改
            DimenCreateManager.makeAll(smallest, value, directory.absolutePath)
        }
        print("生成成功,目录为:${directory.absolutePath}")
    }
}
