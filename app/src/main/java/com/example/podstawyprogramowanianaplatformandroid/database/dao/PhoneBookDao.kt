package com.example.podstawyprogramowanianaplatformandroid.database.dao

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM phone_book_table ORDER BY firstName")
    fun getAllPhoneBook(): List<PhoneBookEntity>

    @Query("SELECT * FROM phone_book_table WHERE id = :phoneBookEntityId")
    fun getPhoneBook(phoneBookEntityId: Int): LiveData<PhoneBookEntity>
}