package com.murqdan.curhatanku.paging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.murqdan.curhatanku.response.ListCurhatanItem

@Database(
    entities = [ListCurhatanItem::class],
    version = 1,
    exportSchema = false
)
abstract class CurhatanDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: CurhatanDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CurhatanDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CurhatanDatabase::class.java, "curhatan_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}