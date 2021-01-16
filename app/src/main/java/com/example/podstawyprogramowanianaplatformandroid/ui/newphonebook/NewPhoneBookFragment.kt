package com.example.podstawyprogramowanianaplatformandroid.ui.newphonebook

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.database.dao.PhoneBookDao
import com.example.podstawyprogramowanianaplatformandroid.database.database.MyRoomDatabase
import com.example.podstawyprogramowanianaplatformandroid.database.entity.PhoneBookEntity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_new_phone_book.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewPhoneBookFragment : Fragment(R.layout.fragment_new_phone_book) {

    private lateinit var phoneBookDao: PhoneBookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

        val database = MyRoomDatabase.getInstance(requireActivity().applicationContext)
        phoneBookDao = database!!.phoneBookDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGenderSpinner()

        bt_save.setOnClickListener {

            val phoneBookEntity = PhoneBookEntity(
                firstName = (til_first_name as TextInputLayout).editText?.text.toString().trim(),
                lastName = (til_last_name as TextInputLayout).editText?.text.toString().trim(),
                number = (til_number as TextInputLayout).editText?.text.toString().trim(),
                email = (til_email as TextInputLayout).editText?.text.toString().trim(),
                gender = (til_gender as TextInputLayout).editText?.text.toString().trim()
            )

            val validation =
                (phoneBookEntity.firstName.isNotEmpty() && phoneBookEntity.lastName.isNotEmpty() && phoneBookEntity.number.isNotEmpty() && phoneBookEntity.gender.isNotEmpty())

            if (validation) {
                CoroutineScope(Dispatchers.IO).launch {
                    phoneBookDao.insert(phoneBookEntity)
                }

                findNavController().navigate(R.id.action_nav_new_phone_book_to_nav_phone_book)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.new_contact_validation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initGenderSpinner() {
        val genders = listOf("K", "M")
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            genders
        )

        filled_exposed_dropdown.setAdapter(adapter)
    }
}