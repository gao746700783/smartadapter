package com.smart.recycler.modules.swipe.repo.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.recycler.modules.swipe.repo.db
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 *                All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * @author  che300
 * @version V1.0
 *          Createdate:    2018-09-14-10:29
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}       che300         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */

@Entity(tableName = "tb_user"
//        ,
//        indices = {@Index(value = {"first_name", "last_name"}, unique = true)}
)
data class User(

        @ColumnInfo(name = "user_id", index = true)
        var userId: Long,

        @ColumnInfo(name = "user_name")
        var userName: String,

        @ColumnInfo(name = "user_age")
        var userAge: String
) {

    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0

    override fun toString(): String {
        return "User(_id=$_id, userId='$userId', userName='$userName', userAge='$userAge')"
    }
}