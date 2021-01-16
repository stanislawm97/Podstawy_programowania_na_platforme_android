package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        private var instance: ReminderDatabase? = null

        fun getInstance(context: Context): ReminderDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    ReminderDatabase::class.java,
                    "reminder_table")
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