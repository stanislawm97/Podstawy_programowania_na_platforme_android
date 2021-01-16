package com.example.podstawyprogramowanianaplatformandroid.ui.phonebook

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.podstawyprogramowanianaplatformandroid.database.database.MyRoomDatabase
import com.example.podstawyprogramowanianaplatformandroid.database.entity.PhoneBookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneBookViewModel : ViewModel() {

    private val _contacts = MutableLiveData<List<PhoneBookEntity>>()
    val contacts = _contacts

    fun getAllReminder(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            _contacts.postValue(
                MyRoomDatabase.getInstance(context.applicationContext)!!.phoneBookDao()
                    .getAllPhoneBook()
            )
        }
    }
}