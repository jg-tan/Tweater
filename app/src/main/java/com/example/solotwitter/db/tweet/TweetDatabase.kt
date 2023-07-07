package com.example.solotwitter.db.tweet

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tweet::class], version = 1)
abstract class TweetDatabase : RoomDatabase() {

    abstract val tweetDAO: TweetDAO

    companion object {
        //makes field immediately visible to other threads
        @Volatile
        private var INSTANCE: TweetDatabase? = null
        fun getInstance(context: Context): TweetDatabase {
            synchronized(this) {
                var instance: TweetDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TweetDatabase::class.java,
                        "tweet_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}