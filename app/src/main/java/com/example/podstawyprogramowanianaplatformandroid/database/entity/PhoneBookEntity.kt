package com.example.podstawyprogramowanianaplatformandroid.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone_book_table")
data class PhoneBookEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    var firstName: String,
    var lastName: String,
    var number: String,
    var email: String? = null,
    var gender: String
)