package com.example.podstawyprogramowanianaplatformandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.podstawyprogramowanianaplatformandroid.database.dao.ReminderDao
import com.example.podstawyprogramowanianaplatformandroid.database.entity.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        private var instance: MyRoomDatabase? = null

        fun getInstance(context: Context): MyRoomDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    MyRoomDatabase::class.java,
                    "ppnpa"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstanceOfDatabase() {
            instance = null
        }
    }
}