package com.smart.recycler.modules.swipe.repo.db

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.util.Log
import java.lang.IllegalArgumentException

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
class AppDatabaseHelper constructor(context: Context) {

    private val DB_NAME = "db_app_test"

    private var MIGRATION_1_2 = object : MigrateDb(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, PRIMARY KEY(`id`))")
        }
    }

    var appDatabase: AppDatabase? = null

    /**
     * 初始化 db
     */
    init {

        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                //.setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                //.fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build()

    }

    /**
     * 获取 user dao
     */
    fun getUserDao(): UserDao {
        return appDatabase?.userDao()!!
    }

    fun getOptionsDao(): OptionsDao {
        return appDatabase?.optionsDao()!!
    }

    /**
     * 单例实现
     */
    companion object {

        var INSTANCE: AppDatabaseHelper? = null

        @Throws(IllegalArgumentException::class)
        fun init(context: Context): AppDatabaseHelper {
            if (INSTANCE == null) {
                synchronized(AppDatabaseHelper::class.java) {
                    if (INSTANCE == null) {
                        if (context !is Application) {
                            throw IllegalArgumentException("can only be called in application and only once...")
                        }

                        INSTANCE = AppDatabaseHelper(context)
                    }
                }
            }
            return INSTANCE!!
        }

    }

    /**
     * 关闭数据库
     */
    fun onDestory() {
        if (appDatabase != null) {
            appDatabase!!.close()
        }
    }

    /**
     *
     */
    open class MigrateDb(sVersion: Int, eVersion: Int) : Migration(sVersion, eVersion) {
        override fun migrate(database: SupportSQLiteDatabase) {
            Log.v("DbHelper", "migrate from $startVersion to $endVersion ...")
        }
    }

}