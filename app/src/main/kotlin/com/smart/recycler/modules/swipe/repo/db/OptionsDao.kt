package com.smart.recycler.modules.swipe.repo.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by jyotidubey on 03/01/18.
 */
@Dao
interface OptionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(options: List<Options>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOption(option: Options)

    @Query("SELECT * FROM options WHERE user_id = :userId")
    fun loadOptionsByUserId(userId: Long): List<Options>

    @Query("SELECT * FROM options")
    fun loadAll(): List<Options>

}