package com.example.podstawyprogramowanianaplatformandroid.database.dao

import androidx.room.*
import com.example.podstawyprogramowanianaplatformandroid.database.entity.PhoneBookEntity

@Dao
interface PhoneBookDao {

    @Insert
    fun insert(phoneBookEntity: PhoneBookEntity)

    @Delete
    fun delete(phoneBookEntity: PhoneBookEntity)

    @Update
    fun update(phoneBookEntity: PhoneBookEntity)

    @Query("SELECT * FROM phone_book_table")
    fun getAllPhoneBook(): List<PhoneBookEntity>
}