package com.smart.recycler.modules.swipe.repo

import com.smart.recycler.modules.swipe.repo.db.Options
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Created by jyotidubey on 06/01/18.
 */
interface OptionsRepo {

    fun isOptionsRepoEmpty(): Observable<Boolean>

    fun insertOptions(options: List<Options>): Observable<Boolean>

    fun insertOption(option: Options): Observable<Boolean>

    fun loadOptions(questionId: Long): Single<List<Options>>

}