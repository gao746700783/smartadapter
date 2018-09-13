package com.smart.recycler.modules.swipe

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.recycler.modules.swipe
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 *                All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * 使用 @Parcelize 简化 parcelable的写法：
 * 1.build.gradle 中增加代码块
 *   android {
 *       androidExtensions {
 *           experimental = true
 *       }
 *   }
 * 2. class 增加 注解@Parcelize
 * 3. 使用插件自动生成相关代码 ，插件名称：
 *
 * @author  che300
 * @version V1.0
 *          Createdate:    2018-09-13-10:28
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}       che300         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
@Parcelize
data class MediaStoreDataKt(val rowId: Long,
                            val title: String,
                            val uri: Uri,
                            val mimeType: String,
                            val dateModified: Long,
                            val orientation: Int,
                            val type: Type,
                            val dateTaken: Long) : Parcelable {

    override fun toString(): String {
        return "MediaStoreDataKt(rowId=$rowId, " +
                "title='$title', " +
                "uri=$uri, " +
                "mimeType='$mimeType', " +
                "dateModified=$dateModified, " +
                "orientation=$orientation, " +
                "type=$type, " +
                "dateTaken=$dateTaken)"
    }
}

/**
 * The type of data.
 */
enum class Type {
    VIDEO,
    IMAGE,
    AUDIO
}