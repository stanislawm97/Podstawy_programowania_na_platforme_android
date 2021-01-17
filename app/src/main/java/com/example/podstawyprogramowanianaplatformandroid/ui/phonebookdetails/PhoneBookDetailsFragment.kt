package com.example.podstawyprogramowanianaplatformandroid.ui.phonebookdetails

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.database.dao.PhoneBookDao
import com.example.podstawyprogramowanianaplatformandroid.database.database.MyRoomDatabase
import com.example.podstawyprogramowanianaplatformandroid.database.entity.PhoneBookEntity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_phone_book_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PhoneBookDetailsFragment : Fragment(R.layout.fragment_phone_book_details) {

    private var phoneBookEntityId: Int? = null
    private lateinit var phoneBookDao: PhoneBookDao
    private lateinit var phoneBookEntityLiveData: LiveData<PhoneBookEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)
        val database = MyRoomDatabase.getInstance(requireActivity().applicationContext)
        phoneBookDao = database!!.phoneBookDao()

        phoneBookEntityId = arguments?.getInt("PhoneBook")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneBookEntityId?.let { phoneBookEntityLiveData = getPhoneBookEntity(it) }
        phoneBookEntityLiveData.observe(viewLifecycleOwner, Observer { handlePhoneBook(it) })
    }

    private fun handlePhoneBook(phoneBookEntity: PhoneBookEntity) {

        (til_first_name as TextInputLayout).editText?.apply {
            setText(phoneBookEntity.firstName)
        }

        (til_last_name as TextInputLayout).editText?.apply {
            setText(phoneBookEntity.lastName)
        }

        (til_number as TextInputLayout).editText?.apply {
            setText(phoneBookEntity.number)
        }

        (til_email as TextInputLayout).editText?.apply {
            setText(phoneBookEntity.email)
        }

        (til_gender as TextInputLayout).editText?.apply {
            setText(phoneBookEntity.gender)
        }

        initGenderSpinner()

        bt_delete_details.setOnClickListener {
            phoneBookEntityLiveData.value?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    phoneBookDao.delete(phoneBookEntity)
                }
            }
            findNavController().navigate(R.id.action_nav_phone_book_details_to_nav_phone_book)
        }

        bt_save_details.setOnClickListener {
            val updatedPhoneBookEntity = PhoneBookEntity(
                id = phoneBookEntityId,
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
                    phoneBookDao.update(updatedPhoneBookEntity)
                }

                findNavController().navigate(R.id.action_nav_phone_book_details_to_nav_phone_book)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.new_contact_validation),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun getPhoneBookEntity(phoneBookEntityId: Int) = runBlocking {
        phoneBookDao.getPhoneBook(phoneBookEntityId)
    }

    private fun initGenderSpinner() {
        val genders = listOf(
            getString(R.string.woman),
            getString(R.string.man),
            getString(R.string.undefined)
        )
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            genders
        )

        filled_exposed_dropdown.setAdapter(adapter)
    }
}