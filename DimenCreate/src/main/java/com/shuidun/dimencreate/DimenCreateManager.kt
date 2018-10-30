package com.shuidun.dimencreate

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal

/**
 * dimens文件生成管理类
 *
 * @author LTP  2018/10/29
 */
object DimenCreateManager {

    private const val XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
    private const val XML_RESOURCE_START = "<resources>\r\n"
    private const val XML_RESOURCE_END = "</resources>\r\n"
    private const val XML_DIMEN_TEMPLATE = "<dimen name=\"dpx_%2\$d\">%3$.2fdp</dimen>\r\n"

    private const val XML_BASE_DPI = "<dimen name=\"base_dpi\">%ddp</dimen>\r\n"
    private const val MAX_SIZE = 720

    /** 生成的文件名 */
    private const val XML_NAME = "dimens.xml"

    /**
     * 生成所有的尺寸数据
     *
     * @param swWidthDp 最小宽度
     * @param designWidth 设计宽度
     * @return
     */
    private fun makeAllDimens(designWidth: Int, swWidthDp: Int): String {
        var dpValue: Float
        var temp: String
        val sb = StringBuilder()
        try {
            sb.append(XML_HEADER)
            sb.append(XML_RESOURCE_START)
            //备份生成的相关信息
            temp = String.format(XML_BASE_DPI, swWidthDp)
            sb.append(temp)
            for (i in 0..MAX_SIZE) {
                dpValue = px2dip(i.toFloat(), swWidthDp, designWidth)
                temp = String.format(XML_DIMEN_TEMPLATE, "", i, dpValue)
                sb.append(temp)
            }
            sb.append(XML_RESOURCE_END)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }


    /**
     * 生成的目标文件夹
     * 只需传宽进来就行
     *
     * @param designWidth     设计宽度
     * @param swWidthDp 最小宽度（Smallest-width）限定符
     * @param buildDir 生成的目标文件夹
     */
    fun makeAll(designWidth: Int, swWidthDp: Int, buildDir: String) {
        try {
            //生成规则
            val folderName: String
            if (swWidthDp > 0) {
                //适配Android 3.2+
                folderName = "values-sw" + swWidthDp + "dp"
            } else {
                return
            }

            //生成目标目录
            val file = File(buildDir + File.separator + folderName)
            if (!file.exists()) {
                file.mkdirs()
            }

            //生成values文件
            val fos = FileOutputStream(file.absolutePath + File.separator + XML_NAME)
            fos.write(makeAllDimens(designWidth, swWidthDp).toByteArray())
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * px转dp
     */
    private fun px2dip(pxValue: Float, sw: Int, designWidth: Int): Float {
        val dpValue = pxValue / designWidth.toFloat() * sw
        val bigDecimal = BigDecimal(dpValue.toDouble())
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()
    }
}
