package com.smart.recycler.modules.swipe.repo

import com.smart.recycler.modules.swipe.repo.db.AppDatabaseHelper
import com.smart.recycler.modules.swipe.repo.db.Options
import com.smart.recycler.modules.swipe.repo.db.OptionsDao
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by jyotidubey on 06/01/18.
 */
class OptionsRepository : OptionsRepo {

    override fun isOptionsRepoEmpty(): Observable<Boolean> =
            Observable.just(optionsDao.loadAll().isEmpty())

    override fun insertOptions(options: List<Options>): Observable<Boolean> {
        optionsDao.insertAll(options)
        return Observable.just(true)
    }

    override fun insertOption(option: Options): Observable<Boolean> {
        optionsDao.insertOption(option)
        return Observable.just(true)
    }

    override fun loadOptions(questionId: Long): Single<List<Options>> =
            Single.fromCallable { optionsDao.loadOptionsByUserId(questionId) }



    private val optionsDao = getOptionsDao()

    private fun getOptionsDao(): OptionsDao {
        return AppDatabaseHelper.INSTANCE?.getOptionsDao()!!
    }

}