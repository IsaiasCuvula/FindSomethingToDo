package com.bersyte.findsomethingtodo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bersyte.findsomethingtodo.models.RandomActivity


@Database(entities = [RandomActivity::class], version = 1)
abstract class FavActDatabase : RoomDatabase() {

    abstract fun favActDao(): FavActDao

    companion object {
        @Volatile
        private var INSTANCE: FavActDatabase? = null
        fun getDatabase(context: Context): FavActDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavActDatabase::class.java,
                    "activity_db1"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


}