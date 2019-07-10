package com.ltp.viewandutils.utils

import android.content.Context
import android.os.Environment
import java.io.File
import android.os.Environment.MEDIA_MOUNTED


/**
 * 文件相关工具类
 *
 * @author LTP  2018/10/15
 */
object FileUtil {

    /**
     * 获取缓存大小
     * @param context
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getTotalCacheSize(context: Context): String {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == MEDIA_MOUNTED) {
            cacheSize += getFolderSize(context.externalCacheDir!!)
        }
        return getFormatSize(cacheSize)
    }

    /**
     * 清除缓存
     * @param context
     */
    fun clearAllCache(context: Context) {
        delete(context.cacheDir.absolutePath)
        if (Environment.getExternalStorageState() == MEDIA_MOUNTED) {
            delete(context.externalCacheDir!!.absolutePath)
        }
    }

    /**
     * 获取文件大小
     *
     * @param file File
     */
    @Throws(Exception::class)
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (i in fileList.indices) {
                // 如果下面还有文件
                size += if (fileList[i].isDirectory) {
                    getFolderSize(fileList[i])
                } else {
                    fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 转换文件大小B-KB-MB-GB
     */
    fun getFormatSize(size: Long): String {
        var newSize = size
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (newSize < 1024) {
            return newSize.toString() + "B"
        } else {
            newSize /= 1024
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (newSize < 1024) {
            return newSize.toString() + "KB"
        } else {
            newSize /= 1024
        }
        return if (newSize < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            newSize *= 100
            ((newSize / 100).toString() + "."
                    + (newSize % 100).toString() + "MB")
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            newSize = newSize * 100 / 1024
            ((newSize / 100).toString() + "."
                    + (newSize % 100).toString() + "GB")
        }
    }

    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    fun delete(delFile: String): Boolean {
        val file = File(delFile)
        return if (!file.exists()) {
            LogUtil.e("删除文件失败:${delFile}不存在！")
            false
        } else {
            if (file.isFile)
                deleteSingleFile(delFile)
            else
                deleteDirectory(delFile)
        }
    }

    /** 删除单个文件
     * @param filePath 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private fun deleteSingleFile(filePath: String): Boolean {
        val file = File(filePath)
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        return if (file.exists() && file.isFile) {
            if (file.delete()) {
                LogUtil.d("删除单个文件${filePath}成功")
                true
            } else {
                LogUtil.e("删除单个文件${filePath}失败")
                false
            }
        } else {
            LogUtil.e("删除单个文件失败,${filePath}不存在")
            false
        }
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private fun deleteDirectory(filePath: String): Boolean {
        var filePathTemp = filePath
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePathTemp.endsWith(File.separator))
            filePathTemp = filePath + File.separator
        val dirFile = File(filePathTemp)
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory)) {
            LogUtil.e("删除目录失败：${filePathTemp}不存在!")
            return false
        }
        var flag = true
        // 删除文件夹中的所有文件包括子目录
        val files = dirFile.listFiles()
        for (file in files) {
            // 删除子文件
            if (file.isFile) {
                flag = deleteSingleFile(file.absolutePath)
                if (!flag)
                    break
            } else {
                // 删除子目录
                if (file.isDirectory) {
                    flag = deleteDirectory(file.absolutePath)
                    if (!flag)
                        break
                }
            }
        }
        if (!flag) {
            LogUtil.e("删除目录失败！")
            return false
        }
        // 删除当前目录
        return if (dirFile.delete()) {
            LogUtil.d("删除目录${filePathTemp}成功！")
            true
        } else {
            LogUtil.e("删除目录：${filePathTemp}失败！")
            false
        }
    }
}