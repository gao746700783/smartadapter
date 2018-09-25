package com.smart.recycler.modules.swipe.repo.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jyotidubey on 03/01/18.
 */
@Entity(tableName = "options",
        foreignKeys = [
            (ForeignKey(entity = User::class,
                    parentColumns = arrayOf("_id"),
                    childColumns = arrayOf("user_id"),
                    onDelete = ForeignKey.CASCADE))]
)
data class Options(

        @ColumnInfo(name = "user_id")
        var userId: Long,

        @ColumnInfo(name = "option_id")
        var optionId: Long,

        @ColumnInfo(name = "option_text")
        var optionText: String

) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var nothing: String = ""

}


