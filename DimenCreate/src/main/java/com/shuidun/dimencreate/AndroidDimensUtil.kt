package com.shuidun.dimencreate

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintWriter

/**
 * Android屏幕适配Dimens文件生成工具类
 * 参考:http://blog.csdn.net/guozhaohui628/article/details/71870530
 * http://blog.csdn.net/lmj623565791/article/details/45460089
 *
 * @author LTP 2017年8月15日
 */
object AndroidDimensUtil {

    /**
     * 基准宽度和高度(通常设置成UI图的分辨率的高度和宽度)
     */
    private const val baseHeight = 792
    private const val baseWidth = 376

    /**
     * 生成Dimens文件的路径
     */
    private var FILE_PATH = File("MyValuesPx").absolutePath

    private const val WidthTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n"
    private const val HeightTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n"

    @JvmStatic
    fun main(args: Array<String>) {
        createDimens(480, 320)
        createDimens(800, 480)
        createDimens(800, 600)
        createDimens(854, 480)
        createDimens(854, 540)
        createDimens(960, 540)
        createDimens(960, 640)
        createDimens(1024, 600)
        createDimens(1024, 768)
        createDimens(1184, 720)
        createDimens(1196, 720)
        createDimens(1208, 720)
        createDimens(1280, 720)
        createDimens(1280, 768)
        createDimens(1280, 800)
        createDimens(1334, 750)
        createDimens(1440, 900)
        createDimens(1700, 1080)
        createDimens(1776, 1080)
        createDimens(1794, 1080)
        createDimens(1800, 1080)
        createDimens(1812, 1080)
        createDimens(1920, 1080)
        createDimens(1920, 1200)
        createDimens(2160, 1080)
        createDimens(2560, 1440)
        createDimens(2560, 1600)

        // 添加以上分辨率都木有通用的,参考:http://blog.csdn.net/guozhaohui628/article/details/71870530
        createCommonDimens(2)
        print("生成成功,目录为:$FILE_PATH")
    }

    /**
     * 生成相应分辨率的文件
     *
     * @param dimenHeight 要生成相应分辨率的高Ø
     * @param dimenWidth  要生成相应分辨率的宽
     */
    private fun createDimens(dimenHeight: Int, dimenWidth: Int) {
        // 生成Height
        val sbForHeight = StringBuilder()
        // 生成头
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n")
        // 计算倍数
        val scaleHeight = dimenHeight * 1.0f / baseHeight
        //        System.out.println("生成Height : " + dimenHeight + " , 基准Height : " + baseHeight + " , height比例 : " + scaleHeight);
        for (i in 1 until baseHeight) {
            // 根据倍率（最终保留两位小数）生成
            sbForHeight.append(HeightTemplate.replace("{0}", i.toString() + "").replace("{1}", leftTwoDecimal(scaleHeight * i).toString() + ""))
        }
        // 最后一个直接写成相应的高，不用计算
        sbForHeight.append(HeightTemplate.replace("{0}", baseHeight.toString() + "").replace("{1}", dimenHeight.toString() + ""))
        sbForHeight.append("</resources>")

        // 生成Width
        val sbForWidth = StringBuilder()
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n")

        val scaleWidth = dimenWidth * 1.0f / baseWidth
        //        System.out.println("生成Width : " + dimenWidth + " , 基准Width : " + baseWidth + " , width比例 : " + scaleWidth);

        for (i in 1 until baseWidth) {
            sbForWidth.append(WidthTemplate.replace("{0}", i.toString() + "").replace("{1}", leftTwoDecimal(scaleWidth * i).toString() + ""))
        }
        sbForWidth.append(WidthTemplate.replace("{0}", baseWidth.toString() + "").replace("{1}", dimenWidth.toString() + ""))
        sbForWidth.append("</resources>")

        // 生成文件
        val dimenFile = File(FILE_PATH + File.separator + "values-" + dimenHeight + "x" + dimenWidth)
        dimenFile.mkdirs()
        println("指定分辨率:" + dimenHeight + "x" + dimenWidth)

        val lay_xFile = File(dimenFile.absolutePath, "lay_x.xml")
        val lay_yFile = File(dimenFile.absolutePath, "lay_y.xml")

        try {
            var printWriter = PrintWriter(FileOutputStream(lay_xFile))
            printWriter.print(sbForWidth.toString())
            printWriter.close()

            printWriter = PrintWriter(FileOutputStream(lay_yFile))
            printWriter.print(sbForHeight.toString())
            printWriter.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    /**
     * 生成适配未找到对应分辨率设备的通用dimen文件（直接放置在values中，单位为px）
     *
     * @param commonDensity 通用density的值
     */
    private fun createCommonDimens(commonDensity: Int) {
        val commonScale = 1.0f / commonDensity

        // 生成Height
        val sbForHeight = StringBuilder()
        // 生成头
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n")
        for (i in 1 until baseHeight + 1) {
            // 根据倍率（最终保留两位小数）生成
            sbForHeight.append(HeightTemplate.replace("{0}", i.toString() + "").replace("{1}", leftTwoDecimal(commonScale * i).toString() + "").replace("px", "dp"))
        }
        sbForHeight.append("</resources>")

        // 生成Width
        val sbForWidth = StringBuilder()
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n")

        for (i in 1 until baseWidth + 1) {
            sbForWidth.append(WidthTemplate.replace("{0}", i.toString() + "").replace("{1}", leftTwoDecimal(commonScale * i).toString() + "").replace("px", "dp"))
        }
        sbForWidth.append("</resources>")

        // 生成文件
        val dimenFile = File(FILE_PATH + File.separator + "values")
        dimenFile.mkdirs()
        println("未指定的通用分辨率（values中）")

        val lay_xFile = File(dimenFile.absolutePath, "lay_x.xml")
        val lay_yFile = File(dimenFile.absolutePath, "lay_y.xml")

        try {
            var printWriter = PrintWriter(FileOutputStream(lay_xFile))
            printWriter.print(sbForWidth.toString())
            printWriter.close()

            printWriter = PrintWriter(FileOutputStream(lay_yFile))
            printWriter.print(sbForHeight.toString())
            printWriter.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    /**
     * 保留两位小数
     *
     * @param a 要保留的Float数值
     * @return 保留后的数值
     */
    private fun leftTwoDecimal(a: Float): Float {
        return (a * 100).toInt() / 100f
    }
}