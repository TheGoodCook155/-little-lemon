package com.course.capstone.littlelemon.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MealsDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealsDao(): DbDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    "db"
                ).fallbackToDestructiveMigration()
//                 .addCallback(roomCallback)
                 .build()
            }

            return instance!!

        }

//        private val roomCallback = object : Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                populateDatabase(instance!!)
//            }
//        }
//
//        private fun populateDatabase(db: AppDatabase) {
//
//
//        }
    }

}
