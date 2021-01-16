package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import androidx.lifecycle.LiveData
import androidx.room.*

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