package com.example.podstawyprogramowanianaplatformandroid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.podstawyprogramowanianaplatformandroid.database.entity.ReminderEntity

@Dao
interface ReminderDao {

    @Insert
    fun insert(reminderEntity: ReminderEntity)

    @Delete
    fun delete(reminderEntity: ReminderEntity)

    @Update
    fun update(reminderEntity: ReminderEntity)

    @Query("SELECT * FROM reminder_table")
    fun getAllReminder(): LiveData<List<ReminderEntity>>
}