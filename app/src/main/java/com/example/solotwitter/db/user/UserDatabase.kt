package com.example.solotwitter.db.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDAO: UserDAO

    companion object {
        //makes field immediately visible to other threads
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance: UserDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}