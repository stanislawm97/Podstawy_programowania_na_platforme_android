package com.example.podstawyprogramowanianaplatformandroid.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class ReminderEntity(
    @PrimaryKey
    val id: Int,
    var title: String,
    var text: String,
    var requestCode: Int,
    var flage: Int,
    var time: String
)