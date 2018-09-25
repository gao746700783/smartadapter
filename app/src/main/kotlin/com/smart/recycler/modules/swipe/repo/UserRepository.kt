package com.smart.recycler.modules.swipe.repo

import com.smart.recycler.modules.swipe.repo.db.AppDatabaseHelper
import com.smart.recycler.modules.swipe.repo.db.User
import com.smart.recycler.modules.swipe.repo.db.UserDao
import io.reactivex.Observable

/**
 * Created by jyotidubey on 06/01/18.
 */
class UserRepository : UserRepo {

    override fun insertUser(user: User): Observable<Boolean> {
        userDao.insert(user)
        return Observable.just(true)
    }

    fun loadAllUser(): Observable<List<User>> {
        return Observable.fromCallable { userDao.loadAll() }
    }


    private val userDao = getUserDao()

    private fun getUserDao(): UserDao {
        return AppDatabaseHelper.INSTANCE?.getUserDao()!!
    }

}