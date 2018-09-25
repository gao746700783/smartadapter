package com.smart.recycler.modules.swipe.repo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

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
 *          Createdate:    2018-09-14-11:22
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}       che300         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
@Database(entities = [
    (User::class), (Options::class)]
        , version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun optionsDao(): OptionsDao

    abstract fun userDao(): UserDao

}