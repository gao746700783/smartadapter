package com.smart.recycler.modules.swipe.repo.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by jyotidubey on 04/01/18.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert
    fun insert(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(question: List<User>)

    @Query("SELECT * FROM tb_user")
    fun loadAll(): List<User>


    //    @Update
    //    void update(User... users);
    //
    //    @Delete
    //    void delete(User... users);
    //
    //    @Query("SELECT * FROM user")
    //    List<User> getAllUsers();
    //
    //    @Query("SELECT * FROM user WHERE id=:id")
    //    User getUser(int id);
    //
    //    @Query("SELECT * FROM user")
    //    Cursor getUserCursor();
    //
    //    @Query("SELECT * FROM user WHERE age=:age")
    //    List<User> getUsersByAge(int age);
    //
    //    @Query("SELECT * FROM user WHERE age=:age LIMIT :max")
    //    List<User> getUsersByAge(int max, int... age);


}