package com.pardeep.roommvvmpractice.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pardeep.roommvvmpractice.data.dao.RoomDao
import com.pardeep.roommvvmpractice.data.model.UserDataModel

@Database(entities = [UserDataModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context,
                    AppDataBase::class.java,
                    name = "AppDataBase",
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}
