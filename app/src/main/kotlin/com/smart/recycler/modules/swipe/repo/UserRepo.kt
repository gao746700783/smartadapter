package com.smart.recycler.modules.swipe.repo

import com.smart.recycler.modules.swipe.repo.db.User
import io.reactivex.Observable


/**
 * Created by jyotidubey on 06/01/18.
 */
interface UserRepo {

    fun insertUser(user: User): Observable<Boolean>

}